package entity.channel.repository

import entity.channel._

trait ChannelRepository {
  def findAll(): List[Channel]
  def findBy(channelId: ChannelId): Option[Channel]
  def create(user: Channel): Unit
}
