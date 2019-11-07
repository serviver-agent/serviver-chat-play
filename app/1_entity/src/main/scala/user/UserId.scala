package entity.user

import java.util.UUID

sealed trait UserId {
  def value: UUID
  final def display: String = value.toString
}

case class VerifiedUserId(value: UUID) extends UserId

case class GeneratedUserId(value: UUID) extends UserId
object GeneratedUserId {
  def create() = GeneratedUserId(UUID.randomUUID())
}

case class UnverifiedUserId(value: UUID) extends UserId
object UnverifiedUserId {
  def fromString(userIdStr: String) = UnverifiedUserId(UUID.fromString(userIdStr))
}
