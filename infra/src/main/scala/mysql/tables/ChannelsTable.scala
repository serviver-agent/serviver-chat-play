package infra.mysql.tables

import scalikejdbc._
import skinny.orm.SkinnyCRUDMapper

object ChannelsTable extends SkinnyCRUDMapper[ChannelRecord] {
  override lazy val tableName = "Channels"
  override lazy val defaultAlias = createAlias("c")
  override def extract(rs: WrappedResultSet, rn: ResultName[ChannelRecord]): ChannelRecord = autoConstruct(rs, rn)
}
