package infra.mysql.tables

import java.util.UUID
import models.user.{GeneratedUserToken, VerifiedUserId}

case class UserTokenRecord(
    userToken: String,
    userId: String
) {
  def userIdToEntity: VerifiedUserId = {
    VerifiedUserId(UUID.fromString(userId))
  }
}
object UserTokenRecord {
  def createWithRandomToken(userId: VerifiedUserId): UserTokenRecord = {
    val token = GeneratedUserToken.createRandomToken()
    UserTokenRecord(userId.display, token.display)
  }
}
