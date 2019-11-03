package models.user

import java.util.UUID

trait UserId {
  def uuid: UUID
  final def display: String = uuid.toString
}
trait UnverifiedUserId extends UserId
object UnverifiedUserId {
  def fromString(userIdStr: String): UnverifiedUserId = {
    new UnverifiedUserId {
      override def uuid = UUID.fromString(userIdStr)
    }
  }
}
trait VerifiedUserId extends UserId

trait _UserId {
  def userId: UserId
}

