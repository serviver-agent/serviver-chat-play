package infra.mysql

import models.{Channel, ChannelId, ChannelRepository}
import scalikejdbc._

class ChannelRepositoryImpl extends ChannelRepository {

  import ChannelRepositoryImpl._

  val c = ChannelsTable.syntax

  def findAll(): List[Channel] = {
    val records = DB.readOnly { implicit session =>
      withSQL {
        select(c.result.*).from(ChannelsTable as c)
      }.map(ChannelRecord(c.resultName)).list.apply()
    }
    records.map(_.toEntity)
  }

  def findBy(channelId: ChannelId): Option[Channel] = {
    val id = channelId.display
    val records = DB.readOnly { implicit session =>
      withSQL {
        select(c.result.*).from(ChannelsTable as c)
          .where.eq(c.id, id)
      }.map(ChannelRecord(c.resultName)).single.apply()
    }
    records.map(_.toEntity)
  }

  def create(channel: Channel) = {
    val record = ChannelRecord.fromEntity(channel)
    DB.localTx { implicit session =>
      withSQL {
        insert.into(ChannelsTable).namedValues(record.columnsAndValues: _*)
      }.update.apply()
    }
  }

}

object ChannelRepositoryImpl {

  case class ChannelRecord(id: String, name: String) {
    import java.util.UUID
    def toEntity = {
      Channel(ChannelId(UUID.fromString(id)), name)
    }
    def columnsAndValues = List(
      ChannelsTable.syntax.id   -> this.id,
      ChannelsTable.syntax.name -> this.name
    )
  }

  object ChannelRecord {
    def fromEntity(entity: Channel): ChannelRecord = {
      ChannelRecord(entity.id.display, entity.name)
    }

    def apply(c: SyntaxProvider[ChannelRecord])(rs: WrappedResultSet): ChannelRecord = {
      apply(c.resultName)(rs)
    }
    def apply(c: ResultName[ChannelRecord])(rs: WrappedResultSet): ChannelRecord = {
      new ChannelRecord(
        rs.get(c.id),
        rs.get(c.name)
      )
    }
  }

  object ChannelsTable extends SQLSyntaxSupport[ChannelRecord] {
    override val tableName = "channels"
  }

}
