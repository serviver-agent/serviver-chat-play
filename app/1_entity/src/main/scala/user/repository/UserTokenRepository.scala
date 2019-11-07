package entity.user.repository

import entity.user._

trait UserTokenRepository {
  def findBy(userToken: UnverifiedUserToken): Option[VerifiedUserId]
  def create(userId: VerifiedUserId): VerifiedUserToken
  def delete(userToken: UserToken): Unit
  def deleteBy(userId: VerifiedUserId): Unit
}
