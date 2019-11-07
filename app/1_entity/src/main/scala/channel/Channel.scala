package entity.channel

import java.util.UUID

case class ChannelId(uuid: UUID) {
  def display = uuid.toString
}
object ChannelId {
  def createFromString(str: String): ChannelId = {
    ChannelId(UUID.fromString(str))
  }
}
case class Channel(id: ChannelId, name: String)
object Channel {
  def createFromName(name: String): Channel = {
    val id = ChannelId(UUID.randomUUID())
    Channel(id, name)
  }
}
