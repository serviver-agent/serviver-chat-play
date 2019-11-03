package models.user.repository

import models.user._

trait UserInfoRepository {

  def findBy(userId: UnverifiedUserId): Option[VerifiedUserId with UserInfo]
  def create(userId: GeneratedUserId, userInfo: UserInfo): Either[Exception, Unit]

}
