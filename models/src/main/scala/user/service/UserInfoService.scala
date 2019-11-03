package models.user.service

import models.user._

trait UserInfoService {

  def findBy(userId: UserId): Option[VerifiedUserId with UserInfo]
  def update(userId: VerifiedUserId with UserInfo): Unit

}
