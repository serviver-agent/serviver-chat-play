package models.user

trait UserAuth {
  def email: String
  def hashedPassword: String
}
trait _UserAuth {
  def userAuth: UserAuth
}

object UserAuth {

  def create(emailStr: String, rawPassword: String): Either[List[Exception], UserAuth] = ???

}
