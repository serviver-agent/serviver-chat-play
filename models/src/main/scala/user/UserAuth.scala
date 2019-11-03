package models.user

case class UserAuth(
    email: UserAuth.Email,
    hashedPassword: UserAuth.HashedPassword
)

object UserAuth {

  def createFromRawPassword(emailStr: String, rawPassword: String) = UserAuth(
    Email(emailStr),
    HashedPassword.fromRawPassword(rawPassword)
  )

  case class Email(value: String)
  case class HashedPassword(value: String)
  object HashedPassword {
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
    private val bcrypt = new BCryptPasswordEncoder()

    def fromRawPassword(rawPassword: String): HashedPassword = {
      HashedPassword(bcrypt.encode(rawPassword))
    }
  }

}
