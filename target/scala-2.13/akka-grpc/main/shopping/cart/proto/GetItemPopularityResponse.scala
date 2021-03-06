// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package shopping.cart.proto

@SerialVersionUID(0L)
final case class GetItemPopularityResponse(
    itemId: _root_.scala.Predef.String = "",
    popularityCount: _root_.scala.Long = 0L,
    unknownFields: _root_.scalapb.UnknownFieldSet = _root_.scalapb.UnknownFieldSet.empty
    ) extends scalapb.GeneratedMessage with scalapb.lenses.Updatable[GetItemPopularityResponse] {
    @transient
    private[this] var __serializedSizeCachedValue: _root_.scala.Int = 0
    private[this] def __computeSerializedValue(): _root_.scala.Int = {
      var __size = 0
      
      {
        val __value = itemId
        if (!__value.isEmpty) {
          __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(1, __value)
        }
      };
      
      {
        val __value = popularityCount
        if (__value != 0L) {
          __size += _root_.com.google.protobuf.CodedOutputStream.computeInt64Size(2, __value)
        }
      };
      __size += unknownFields.serializedSize
      __size
    }
    override def serializedSize: _root_.scala.Int = {
      var read = __serializedSizeCachedValue
      if (read == 0) {
        read = __computeSerializedValue()
        __serializedSizeCachedValue = read
      }
      read
    }
    def writeTo(`_output__`: _root_.com.google.protobuf.CodedOutputStream): _root_.scala.Unit = {
      {
        val __v = itemId
        if (!__v.isEmpty) {
          _output__.writeString(1, __v)
        }
      };
      {
        val __v = popularityCount
        if (__v != 0L) {
          _output__.writeInt64(2, __v)
        }
      };
      unknownFields.writeTo(_output__)
    }
    def withItemId(__v: _root_.scala.Predef.String): GetItemPopularityResponse = copy(itemId = __v)
    def withPopularityCount(__v: _root_.scala.Long): GetItemPopularityResponse = copy(popularityCount = __v)
    def withUnknownFields(__v: _root_.scalapb.UnknownFieldSet) = copy(unknownFields = __v)
    def discardUnknownFields = copy(unknownFields = _root_.scalapb.UnknownFieldSet.empty)
    def getFieldByNumber(__fieldNumber: _root_.scala.Int): _root_.scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => {
          val __t = itemId
          if (__t != "") __t else null
        }
        case 2 => {
          val __t = popularityCount
          if (__t != 0L) __t else null
        }
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      _root_.scala.Predef.require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => _root_.scalapb.descriptors.PString(itemId)
        case 2 => _root_.scalapb.descriptors.PLong(popularityCount)
      }
    }
    def toProtoString: _root_.scala.Predef.String = _root_.scalapb.TextFormat.printToUnicodeString(this)
    def companion = shopping.cart.proto.GetItemPopularityResponse
}

object GetItemPopularityResponse extends scalapb.GeneratedMessageCompanion[shopping.cart.proto.GetItemPopularityResponse] {
  implicit def messageCompanion: scalapb.GeneratedMessageCompanion[shopping.cart.proto.GetItemPopularityResponse] = this
  def merge(`_message__`: shopping.cart.proto.GetItemPopularityResponse, `_input__`: _root_.com.google.protobuf.CodedInputStream): shopping.cart.proto.GetItemPopularityResponse = {
    var __itemId = `_message__`.itemId
    var __popularityCount = `_message__`.popularityCount
    var `_unknownFields__`: _root_.scalapb.UnknownFieldSet.Builder = null
    var _done__ = false
    while (!_done__) {
      val _tag__ = _input__.readTag()
      _tag__ match {
        case 0 => _done__ = true
        case 10 =>
          __itemId = _input__.readStringRequireUtf8()
        case 16 =>
          __popularityCount = _input__.readInt64()
        case tag =>
          if (_unknownFields__ == null) {
            _unknownFields__ = new _root_.scalapb.UnknownFieldSet.Builder(_message__.unknownFields)
          }
          _unknownFields__.parseField(tag, _input__)
      }
    }
    shopping.cart.proto.GetItemPopularityResponse(
        itemId = __itemId,
        popularityCount = __popularityCount,
        unknownFields = if (_unknownFields__ == null) _message__.unknownFields else _unknownFields__.result()
    )
  }
  implicit def messageReads: _root_.scalapb.descriptors.Reads[shopping.cart.proto.GetItemPopularityResponse] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      _root_.scala.Predef.require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      shopping.cart.proto.GetItemPopularityResponse(
        itemId = __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).map(_.as[_root_.scala.Predef.String]).getOrElse(""),
        popularityCount = __fieldsMap.get(scalaDescriptor.findFieldByNumber(2).get).map(_.as[_root_.scala.Long]).getOrElse(0L)
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = ShoppingCartServiceProto.javaDescriptor.getMessageTypes.get(4)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = ShoppingCartServiceProto.scalaDescriptor.messages(4)
  def messageCompanionForFieldNumber(__number: _root_.scala.Int): _root_.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__number)
  lazy val nestedMessagesCompanions: Seq[_root_.scalapb.GeneratedMessageCompanion[_ <: _root_.scalapb.GeneratedMessage]] = Seq.empty
  def enumCompanionForFieldNumber(__fieldNumber: _root_.scala.Int): _root_.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = shopping.cart.proto.GetItemPopularityResponse(
    itemId = "",
    popularityCount = 0L
  )
  implicit class GetItemPopularityResponseLens[UpperPB](_l: _root_.scalapb.lenses.Lens[UpperPB, shopping.cart.proto.GetItemPopularityResponse]) extends _root_.scalapb.lenses.ObjectLens[UpperPB, shopping.cart.proto.GetItemPopularityResponse](_l) {
    def itemId: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Predef.String] = field(_.itemId)((c_, f_) => c_.copy(itemId = f_))
    def popularityCount: _root_.scalapb.lenses.Lens[UpperPB, _root_.scala.Long] = field(_.popularityCount)((c_, f_) => c_.copy(popularityCount = f_))
  }
  final val ITEMID_FIELD_NUMBER = 1
  final val POPULARITYCOUNT_FIELD_NUMBER = 2
  def of(
    itemId: _root_.scala.Predef.String,
    popularityCount: _root_.scala.Long
  ): _root_.shopping.cart.proto.GetItemPopularityResponse = _root_.shopping.cart.proto.GetItemPopularityResponse(
    itemId,
    popularityCount
  )
  // @@protoc_insertion_point(GeneratedMessageCompanion[shoppingcart.GetItemPopularityResponse])
}
