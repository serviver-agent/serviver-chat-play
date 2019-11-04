package infra.mysql.tables

import java.util.UUID
import models.user.{UserId, VerifiedUserId, UserInfo}

case class UserInfoRecord(
    userId: String,
    userName: String,
    imageUrl: String
) {
  def toEntity: (VerifiedUserId, UserInfo) = {
    val verifiedUserId = VerifiedUserId(UUID.fromString(userId))
    val userInfo       = UserInfo.create(userName, imageUrl)
    (verifiedUserId, userInfo)
  }
}

object UserInfoRecord {
  def fromEntity(userId: UserId, userInfo: UserInfo) = UserInfoRecord(
    userId.display,
    userInfo.userName.display,
    userInfo.imageUrl.display
  )
}
