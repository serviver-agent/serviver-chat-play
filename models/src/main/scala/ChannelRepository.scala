package models

trait ChannelRepository {
  def findAll(): List[Channel]
  def findBy(channelId: ChannelId): Option[Channel]
  def create(user: Channel): Unit
}
