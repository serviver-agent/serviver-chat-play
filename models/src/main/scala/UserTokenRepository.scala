package models

trait UserTokenRepository {
  def findBy(userToken: UserToken): Option[UserId]
  def create(userId: UserId): UserToken
  def delete(userToken: UserToken): Unit
}
