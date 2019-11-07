package entity.user.service

import javax.inject.{Singleton, Inject}

import entity.user._
import entity.user.repository._

@Singleton
class UserTokenService @Inject() (
    repository: UserTokenRepository
) {

  def findBy(userToken: UnverifiedUserToken): Option[VerifiedUserId] = repository.findBy(userToken)
  def create(userId: VerifiedUserId): VerifiedUserToken              = repository.create(userId)
  def delete(userToken: UserToken): Unit                             = repository.delete(userToken)
  def deleteBy(userId: VerifiedUserId): Unit                         = repository.deleteBy(userId)

}
