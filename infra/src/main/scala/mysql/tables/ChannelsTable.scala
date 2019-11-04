package infra.mysql.tables

import java.util.UUID
import models.channel.ChannelId

import scalikejdbc._
import skinny.orm.SkinnyCRUDMapperWithId

object ChannelsTable extends SkinnyCRUDMapperWithId[ChannelId, ChannelRecord] {
  override lazy val tableName           = "Channels"
  override lazy val defaultAlias        = createAlias("c")
  override lazy val primaryKeyFieldName = "id"

  override def extract(rs: WrappedResultSet, rn: ResultName[ChannelRecord]): ChannelRecord = autoConstruct(rs, rn)
  override def idToRawValue(channelId: ChannelId)                                          = channelId.display
  override def rawValueToId(value: Any)                                                    = ChannelId(UUID.fromString(value.toString))
  override def useExternalIdGenerator                                                      = true

  def columnsAndValues(record: ChannelRecord) = List(
    Symbol("id")   -> record.id,
    Symbol("name") -> record.name
  )
}
