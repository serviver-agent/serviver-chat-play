package infra.mysql.tables

import scalikejdbc._
import scalikejdbc.scalatest.AutoRollback
import org.scalatest.fixture.FlatSpec
import org.scalatest._

import models.channel.ChannelId

class ChatChannelsTableSpec extends FlatSpec with Matchers with AutoRollback with TestDBSettings {

  override def fixture(implicit session: DBSession) {
    val sql = SQL("insert into chat_channels(channel_id, channel_name) values (?, ?)")
    sql.bind("985b12a5-0000-0000-0000-000000000001", "chat_01").update.apply()
    sql.bind("985b12a5-0000-0000-0000-000000000002", "chat_02").update.apply()
    sql.bind("985b12a5-0000-0000-0000-000000000003", "chat_03").update.apply()
  }

  it should "select" in { implicit session =>
    val channelId = ChannelId.createFromString("985b12a5-0000-0000-0000-000000000002")
    val recordOption = ChatChannelsTable.findById(channelId)
    val answer = Some(ChatChannelRecord(
      channel_id = "985b12a5-0000-0000-0000-000000000002",
      channel_name = "chat_02",
    ))

    recordOption shouldBe answer
  }

  it should "insert" in { implicit session =>
    val insertRecord = ChatChannelRecord(
      channel_id = "985b12a5-0000-0000-0000-000000000004",
      channel_name = "chat_04",
    )
    val parameters = ChatChannelsTable.columnsAndValues(insertRecord)
    ChatChannelsTable.createWithAttributes(parameters: _*)

    val channelId = ChannelId.createFromString("985b12a5-0000-0000-0000-000000000004")
    val recordOption = ChatChannelsTable.findById(channelId)

    insertRecord shouldBe recordOption.get
  }

  it should "update" in { implicit session =>
    val channelId = ChannelId.createFromString("985b12a5-0000-0000-0000-000000000003")
    ChatChannelsTable.updateById(channelId).withAttributes(
      Symbol("channel_name") -> "chat_03_updated"
    )
    val updatedRecordOption = ChatChannelsTable.findById(channelId)

    val answer = Some(ChatChannelRecord(
      channel_id = "985b12a5-0000-0000-0000-000000000003",
      channel_name = "chat_03_updated"
    ))

    updatedRecordOption shouldBe answer
  }

  it should "delete" in { implicit session =>
    val channelId = ChannelId.createFromString("985b12a5-0000-0000-0000-000000000003")
    ChatChannelsTable.findById(channelId).isDefined shouldBe true

    ChatChannelsTable.deleteById(channelId)
    ChatChannelsTable.findById(channelId).isDefined shouldBe false
  }

}
