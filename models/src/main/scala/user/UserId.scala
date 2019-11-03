package models.user

import java.util.UUID

sealed trait UserId {
  def uuid: UUID
  final def display: String = uuid.toString
}

case class VerifiedUserId(uuid: UUID) extends UserId

case class GeneratedUserId(uuid: UUID) extends UserId
object GeneratedUserId {
  def create() = GeneratedUserId(UUID.randomUUID())
}

case class UnverifiedUserId(uuid: UUID) extends UserId
object UnverifiedUserId {
  def fromString(userIdStr: String) = UnverifiedUserId(UUID.fromString(userIdStr))
}
