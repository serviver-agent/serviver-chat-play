package infra.mysql.tables

import java.util.UUID
import entity.channel.{Channel, ChannelId}

case class ChatChannelRecord(
    channel_id: String,
    channel_name: String
) {
  def toEntity: Channel = {
    Channel(ChannelId(UUID.fromString(channel_id)), channel_name)
  }
}

object ChatChannelRecord {
  def fromEntity(entity: Channel): ChatChannelRecord = {
    ChatChannelRecord(entity.id.display, entity.name)
  }
}
