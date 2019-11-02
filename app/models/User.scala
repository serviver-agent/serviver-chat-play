package models

import java.util.UUID

case class UserId(uuid: UUID)
object UserId {
  def createFromString(str: String): UserId = {
    UserId(UUID.fromString(str))
  }
}

case class User(id: UserId, name: String)
object User {
  def createFromName(name: String): User = {
    val id = UserId(UUID.randomUUID())
    User(id, name)
  }
}
