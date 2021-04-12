package shopping.cart.repository

import scalikejdbc.{DBSession, scalikejdbcSQLInterpolationImplicitDef}
import shopping.cart.proto.{ItemPopularity, ListItemPopularity}

class ItemPopularityRepositoryImpl() extends ItemPopularityRepository {

  override def update(
                       session: ScalikeJdbcSession,
                       itemId: String,
                       delta: Int): Unit = {
    session.db.withinTx { implicit dbSession =>
      // This uses the PostgreSQL `ON CONFLICT` feature
      // Alternatively, this can be implemented by first issuing the `UPDATE`
      // and checking for the updated rows count. If no rows got updated issue
      // the `INSERT` instead.
      sql"""
           INSERT INTO item_popularity (itemid, count) VALUES ($itemId, $delta)
           ON CONFLICT (itemid) DO UPDATE SET count = item_popularity.count + $delta
         """.executeUpdate().apply()
    }
  }

  override def getItem(
                        session: ScalikeJdbcSession,
                        itemId: String): Option[Long] = {
    if (session.db.isTxAlreadyStarted) {
      session.db.withinTx { implicit dbSession =>
        select(itemId)
      }
    } else {
      session.db.readOnly { implicit dbSession =>
        select(itemId)
      }
    }
  }

  private def select(itemId: String)(implicit dbSession: DBSession) = {
    sql"SELECT count FROM item_popularity WHERE itemid = $itemId"
      .map(_.long("count"))
      .toOption()
      .apply()
  }


  override def itemWithPopularity(session: ScalikeJdbcSession) = {
    ListItemPopularity (

      if(session.db.isTxAlreadyStarted) {
        session.db.withinTx { implicit dbSession =>
          sql"SELECT itemid, count FROM item_popularity"
            .map(rs => ItemPopularity(rs.get(1), rs.get(2)))
            .toList()
            .apply()
        }
      } else {
        session.db.readOnly { implicit dbSession =>
          sql"SELECT itemid, count FROM item_popularity"
            .map(rs => ItemPopularity(rs.get(1), rs.get(2)))
            .toList()
            .apply()
        }
      }

    )
  }
}
