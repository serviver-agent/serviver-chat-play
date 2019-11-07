package models.user

case class UserAuth(
    email: UserAuth.Email,
    hashedPassword: UserAuth.HashedPassword
)

object UserAuth {

  def create(emailStr: String, hashedPasswordStr: String) = UserAuth(
    Email(emailStr),
    HashedPassword(hashedPasswordStr)
  )

  def createFromRawPassword(emailStr: String, rawPassword: String) = UserAuth(
    Email(emailStr),
    HashedPassword.fromRawPassword(rawPassword)
  )
  def passwordHiddenUnapply(userAuth: UserAuth): Option[(String, String)] = {
    Some((userAuth.email.display, "************"))
  }

  case class Email(value: String) {
    final def display: String = value
  }
  case class HashedPassword(value: String) {
    final def display: String = value
  }
  object HashedPassword {
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
    private val bcrypt = new BCryptPasswordEncoder()

    def fromRawPassword(rawPassword: String): HashedPassword = {
      HashedPassword(bcrypt.encode(rawPassword))
    }
  }

}
