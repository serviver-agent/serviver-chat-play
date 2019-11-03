package models.user

trait UserToken {
  def value: String
  final def display: String = value
}

trait _UserToken {
  def userToken: UserToken
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

    new UserToken {
      val value = str
    }
  }

}
