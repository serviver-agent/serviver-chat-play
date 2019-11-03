package models.user.service

import javax.inject.{Singleton, Inject}

import models.user._
import models.user.repository._

@Singleton
class UserTokenService @Inject() (
    repository: UserTokenRepository
) {

  def findBy(userToken: UserToken): Option[VerifiedUserId] = repository.findBy(userToken)
  def create(userId: VerifiedUserId): UserToken            = repository.create(userId)
  def delete(userToken: UserToken): Unit                   = repository.delete(userToken)

}
