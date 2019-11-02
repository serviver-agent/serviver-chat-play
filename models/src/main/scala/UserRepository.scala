package models

trait UserRepository {
  def findAll(): List[User]
  def findBy(userId: UserId): Option[User]
  def create(user: User): Unit
}
