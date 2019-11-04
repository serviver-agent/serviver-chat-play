package infra.mysql.tables

import java.util.UUID
import models.user.{UserId, VerifiedUserId, UserAuth}

case class UserAuthRecord(
    userId: String,
    email: String,
    hashedPassword: String
) {
  def toEntity: (VerifiedUserId, UserAuth) = {
    val verifiedUserId = VerifiedUserId(UUID.fromString(userId))
    val userAuth       = UserAuth.create(email, hashedPassword)
    (verifiedUserId, userAuth)
  }

  def userIdToEntity: VerifiedUserId = {
    VerifiedUserId(UUID.fromString(userId))
  }
}

object UserAuthRecord {
  def fromEntity(userId: UserId, userAuth: UserAuth) = UserAuthRecord(
    userId.display,
    userAuth.email.display,
    userAuth.hashedPassword.display
  )
}
