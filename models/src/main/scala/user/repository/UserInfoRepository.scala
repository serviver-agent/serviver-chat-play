package models.user.repository

import models.user._

trait UserInfoRepository {

  def findAll(): List[(VerifiedUserId, UserInfo)]
  def findBy(userId: UnverifiedUserId): Option[(VerifiedUserId, UserInfo)]
  def create(userId: GeneratedUserId, userInfo: UserInfo): Either[Exception, Unit]
  def update(userId: VerifiedUserId, userInfo: UserInfo): Unit

}
