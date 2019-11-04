package infra.mysql.tables

import java.util.UUID
import models.channel.{Channel, ChannelId}

case class ChannelRecord(
    id: String,
    name: String
) {
  def toEntity: Channel = {
    Channel(ChannelId(UUID.fromString(id)), name)
  }
}

object ChannelRecord {
  def fromEntity(entity: Channel): ChannelRecord = {
    ChannelRecord(entity.id.display, entity.name)
  }
}
