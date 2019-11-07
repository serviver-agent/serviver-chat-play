package infra.mysql.tables

import java.util.UUID
import models.user.{GeneratedUserToken, VerifiedUserId}

case class UserTokenRecord(
    user_token: String,
    user_id: String
) {
  def userIdToEntity: VerifiedUserId = {
    VerifiedUserId(UUID.fromString(user_id))
  }
}
object UserTokenRecord {
  def createWithRandomToken(userId: VerifiedUserId): UserTokenRecord = {
    val token = GeneratedUserToken.createRandomToken()
    UserTokenRecord(userId.display, token.display)
  }
}
