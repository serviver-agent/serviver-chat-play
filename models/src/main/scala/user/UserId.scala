package models.user

import java.util.UUID

sealed trait UserId {
  def uuid: UUID
  final def display: String = uuid.toString
}

trait VerifiedUserId extends UserId

trait GeneratedUserId extends UserId
object GeneratedUserId {
  def create(): GeneratedUserId = {
    new GeneratedUserId {
      override def uuid = UUID.randomUUID()
    }
  }
}

trait UnverifiedUserId extends UserId
object UnverifiedUserId {
  def fromString(userIdStr: String): UnverifiedUserId = {
    new UnverifiedUserId {
      override def uuid = UUID.fromString(userIdStr)
    }
  }
}

trait _UserId {
  def userId: UserId
}

