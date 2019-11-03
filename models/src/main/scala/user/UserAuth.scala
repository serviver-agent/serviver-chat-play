package models.user

trait UserAuth {
  def email: String
  def hashedPassword: String
}
trait _UserAuth {
  def userAuth: UserAuth
}
