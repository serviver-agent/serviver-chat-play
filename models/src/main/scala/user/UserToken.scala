package models.user

case class UserToken(value: String) {
  final def display: String = value
}

object UserToken {

  val TokenLength = 64
  val Prefix      = "Bearer "

  def createRandomToken(): UserToken = {
    import util.Random

    val ts = (('A' to 'Z').toList :::
      ('a' to 'z').toList :::
      ('0' to '9').toList :::
      List('-', '.', '_', '~', '+', '/')).toArray
    val tsLen = ts.length
    val str   = Prefix + List.fill(TokenLength - Prefix.length)(ts(Random.nextInt(tsLen))).mkString

    UserToken(str)
  }

}
