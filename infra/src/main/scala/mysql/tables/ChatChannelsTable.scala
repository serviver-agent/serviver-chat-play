package infra.mysql.tables

import java.util.UUID
import models.channel.ChannelId

import scalikejdbc._
import skinny.orm.SkinnyCRUDMapperWithId

object ChatChannelsTable extends SkinnyCRUDMapperWithId[ChannelId, ChatChannelRecord] {
  override lazy val tableName           = "chat_channels"
  override lazy val defaultAlias        = createAlias("c")
  override lazy val primaryKeyFieldName = "channel_id"

  override def extract(rs: WrappedResultSet, rn: ResultName[ChatChannelRecord]): ChatChannelRecord = autoConstruct(rs, rn)
  override def idToRawValue(channelId: ChannelId)                                          = channelId.display
  override def rawValueToId(value: Any)                                                    = ChannelId(UUID.fromString(value.toString))
  override def useExternalIdGenerator                                                      = true

  def columnsAndValues(record: ChatChannelRecord) = List(
    Symbol("channel_id")   -> record.channel_id,
    Symbol("channel_name") -> record.channel_name
  )
}
