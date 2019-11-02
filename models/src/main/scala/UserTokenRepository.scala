package models

trait UserTokenRepository {
  def findBy(token: UserToken): Option[UserId]
  def create(userId: UserId): UserToken
  def delete(token: UserToken): Unit
}
