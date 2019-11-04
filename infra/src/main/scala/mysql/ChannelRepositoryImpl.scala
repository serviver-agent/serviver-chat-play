package infra.mysql

import models.channel.{Channel, ChannelId}
import models.channel.repository.ChannelRepository

import infra.mysql.tables.{ChannelRecord, ChannelsTable}

class ChannelRepositoryImpl extends ChannelRepository {

  val c = ChannelsTable.defaultAlias

  def findAll(): List[Channel] = {
    ChannelsTable.findAll().map(_.toEntity)
  }

  def findBy(channelId: ChannelId): Option[Channel] = {
    ChannelsTable.findById(channelId).map(_.toEntity)
  }

  def create(channel: Channel) = {
    val record     = ChannelRecord.fromEntity(channel)
    val parameters = ChannelsTable.columnsAndValues(record)
    ChannelsTable.createWithAttributes(parameters: _*)
  }

}
