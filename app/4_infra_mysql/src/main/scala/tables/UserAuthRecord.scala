package infra.mysql.tables

import java.util.UUID
import entity.user.{UserId, VerifiedUserId, UserAuth}

case class UserAuthRecord(
    user_id: String,
    email: String,
    hashed_password: String
) {
  def toEntity: (VerifiedUserId, UserAuth) = {
    val verifiedUserId = VerifiedUserId(UUID.fromString(user_id))
    val userAuth       = UserAuth.create(email, hashed_password)
    (verifiedUserId, userAuth)
  }

  def userIdToEntity: VerifiedUserId = {
    VerifiedUserId(UUID.fromString(user_id))
  }
}

object UserAuthRecord {
  def fromEntity(userId: UserId, userAuth: UserAuth) = UserAuthRecord(
    userId.display,
    userAuth.email.display,
    userAuth.hashedPassword.display
  )
}
