package models.user.service

import models.user._

trait UserTokenService {

  def create(userId: UserId): UserToken
  def findBy(userToken: UserToken): Option[UserId]
  def delete(userToken: UserToken): Unit

}
