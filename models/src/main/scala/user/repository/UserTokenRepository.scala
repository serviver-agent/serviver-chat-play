package models.user.repository

import models.user._

trait UserTokenRepository {
  def findBy(userToken: UserToken): Option[VerifiedUserId]
  def create(userId: UserId): UserToken
  def delete(userToken: UserToken): Unit
}
