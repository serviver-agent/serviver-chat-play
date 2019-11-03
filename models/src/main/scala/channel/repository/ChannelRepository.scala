package models.channel.repository

import models.channel._

trait ChannelRepository {
  def findAll(): List[Channel]
  def findBy(channelId: ChannelId): Option[Channel]
  def create(user: Channel): Unit
}
