package models

case class UserToken(value: String)

object UserToken {

  val TokenLength = 64

  def createRandomToken(): UserToken = {
    import util.Random

    val ts = (('A' to 'Z').toList :::
      ('a' to 'z').toList :::
      ('0' to '9').toList :::
      List('-', '.', '_', '~', '+', '/')).toArray

    val tsLen = ts.length

    val str = "Bearer " + List.fill(TokenLength)(ts(Random.nextInt(tsLen))).mkString
    UserToken(str)
  }

}
