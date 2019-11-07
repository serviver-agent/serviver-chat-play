package infra.mysql.tables

import java.util.UUID
import models.user.{UserId, VerifiedUserId, UserInfo}

case class UserInfoRecord(
    user_id: String,
    user_name: String,
    image_url: String
) {
  def toEntity: (VerifiedUserId, UserInfo) = {
    val verifiedUserId = VerifiedUserId(UUID.fromString(user_id))
    val userInfo       = UserInfo.create(user_name, image_url)
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
