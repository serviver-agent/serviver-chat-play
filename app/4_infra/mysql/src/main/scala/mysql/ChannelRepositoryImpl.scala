package infra.mysql

import models.channel.{Channel, ChannelId}
import models.channel.repository.ChannelRepository

import infra.mysql.tables.{ChatChannelRecord, ChatChannelsTable}

class ChannelRepositoryImpl extends ChannelRepository {

  def findAll(): List[Channel] = {
    ChatChannelsTable.findAll().map(_.toEntity)
  }

  def findBy(channelId: ChannelId): Option[Channel] = {
    ChatChannelsTable.findById(channelId).map(_.toEntity)
  }

  def create(channel: Channel) = {
    val record     = ChatChannelRecord.fromEntity(channel)
    val parameters = ChatChannelsTable.columnsAndValues(record)
    ChatChannelsTable.createWithAttributes(parameters: _*)
  }

}
