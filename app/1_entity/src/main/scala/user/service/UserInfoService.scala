package models.user.service

import javax.inject.{Singleton, Inject}

import models.user._
import models.user.repository._

@Singleton
class UserInfoService @Inject() (
    repository: UserInfoRepository
) {

  def findAll(): List[(VerifiedUserId, UserInfo)]                          = repository.findAll()
  def findBy(userId: UnverifiedUserId): Option[(VerifiedUserId, UserInfo)] = repository.findBy(userId)
  def update(userId: VerifiedUserId, userInfo: UserInfo): Unit             = repository.update(userId, userInfo)

}
