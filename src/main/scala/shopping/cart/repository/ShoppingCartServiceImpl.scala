package shopping.cart.repository

import akka.actor.typed.{ActorSystem, DispatcherSelector}
import akka.cluster.sharding.typed.scaladsl.{ClusterSharding, EntityRef}
import akka.grpc.GrpcServiceException
import akka.util.Timeout
import io.grpc.Status
import org.slf4j.LoggerFactory
import shopping.cart.ShoppingCart
import shopping.cart.ShoppingCart.Summary
import shopping.cart.proto._

import java.util.concurrent.TimeoutException
import scala.concurrent.{ExecutionContext, Future}

class ShoppingCartServiceImpl(system: ActorSystem[_],itemPopularityRepository: ItemPopularityRepository)
  extends ShoppingCartService {

  import system.executionContext

  private val logger = LoggerFactory.getLogger(getClass)

  implicit private val timeout: Timeout =
    Timeout.create(
      system.settings.config.getDuration("shopping-cart-service.ask-timeout"))

  private val sharding = ClusterSharding(system)

  override def addItem(in: AddItemRequest): Future[Cart] = {
    logger.info("addItem {} to cart {}", in.itemId, in.cartId)
//    Future.successful(
//      Cart(items = List(Item(in.itemId, in.quantity))))

    val entityRef: EntityRef[ShoppingCart.Command] = sharding.entityRefFor(ShoppingCart.EntityKey, in.cartId)

     // entityRef ask
    //val x: ActorRef[StatusReply[Summary]] => ShoppingCart.AddItem = ShoppingCart.AddItem(in.itemId, in.quantity, _)

    val reply: Future[ShoppingCart.Summary] = entityRef.askWithStatus[Summary](ShoppingCart.AddItem(in.itemId, in.quantity, _))
    val response: Future[Cart] = reply.map(cart => toProtoCart(cart))
    convertError(response)
  }

  private def toProtoCart(cart: ShoppingCart.Summary): Cart = {
    Cart(cart.items.iterator.map { case (itemId, quantity) =>
      Item(itemId, quantity)
    }.toSeq,
      cart.checkedOut)
  }

  private def convertError[T](response: Future[T]): Future[T] = {
    response.recoverWith {
      case _: TimeoutException =>
        Future.failed(
          new GrpcServiceException(
            Status.UNAVAILABLE.withDescription("Operation timed out")))
      case exc =>
        Future.failed(
          new GrpcServiceException(
            Status.INVALID_ARGUMENT.withDescription(exc.getMessage)))
    }
  }

  override def checkout(in: CheckoutRequest): Future[Cart] = {
    logger.info("checkout {}", in.cartId)
    val entityRef = sharding.entityRefFor(ShoppingCart.EntityKey, in.cartId)
    val reply: Future[ShoppingCart.Summary] =
      entityRef.askWithStatus(ShoppingCart.Checkout(_))
    val response = reply.map(cart => toProtoCart(cart))
    convertError(response)
  }

  override def getCart(in: GetCartRequest): Future[Cart] = {
    logger.info("getCart {}", in.cartId)
    val entityRef = sharding.entityRefFor(ShoppingCart.EntityKey, in.cartId)
    val response = entityRef.ask(ShoppingCart.Get).map { cart =>
        if (cart.items.isEmpty)
          throw new GrpcServiceException(
            Status.NOT_FOUND.withDescription(s"Cart ${in.cartId} not found"))
        else
          toProtoCart(cart)
      }
    convertError(response)
  }

  private val blockingJdbcExecutor: ExecutionContext =
    system.dispatchers.lookup(
      DispatcherSelector
        .fromConfig("akka.projection.jdbc.blocking-jdbc-dispatcher")
    )

  override def getItemPopularity(in: GetItemPopularityRequest)
  : Future[GetItemPopularityResponse] =
  {
    Future {
      ScalikeJdbcSession.withSession { session =>
        itemPopularityRepository.getItem(session, in.itemId)
      }
    }(blockingJdbcExecutor).map {
      case Some(count) =>
        GetItemPopularityResponse(in.itemId, count)
      case None =>
        GetItemPopularityResponse(in.itemId)
    }
  }

}
