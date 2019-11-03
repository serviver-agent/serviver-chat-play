package models.user

sealed trait UserToken {
  def value: String
  final def display: String = value
}

object UserToken {
  val TokenLength = 64
  val Prefix      = "Bearer "
}

case class VerifiedUserToken(value: String) extends UserToken

case class GeneratedUserToken(value: String) extends UserToken
object GeneratedUserToken {

  def createRandomToken(): GeneratedUserToken = {
    import util.Random

    val ts = (('A' to 'Z').toList :::
      ('a' to 'z').toList :::
      ('0' to '9').toList :::
      List('-', '.', '_', '~', '+', '/')).toArray
    val tsLen = ts.length
    val str = UserToken.Prefix + List
      .fill(UserToken.TokenLength - UserToken.Prefix.length)(ts(Random.nextInt(tsLen)))
      .mkString

    GeneratedUserToken(str)
  }

}

case class UnverifiedUserToken(value: String) extends UserToken
object UnverifiedUserToken {
  def fromString(str: String) = UnverifiedUserToken(str)
}
