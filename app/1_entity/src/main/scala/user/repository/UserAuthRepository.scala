package entity.user.repository

import entity.user._

trait UserAuthRepository {

  import UserAuthRepository._

  def findBy(userId: UnverifiedUserId): Option[(VerifiedUserId, UserAuth)]
  def findBy(userAuth: UserAuth): Option[VerifiedUserId]
  def create(userId: GeneratedUserId, userAuth: UserAuth): Either[CreateError, VerifiedUserId]

}

object UserAuthRepository {

  abstract class CreateError(message: String, cause: Throwable = new Throwable) extends Exception(message, cause)
  case class DuplicateUserError(cause: Throwable)                               extends CreateError("DuplicateUserError", cause)
  case class UnknownError(cause: Throwable)                                     extends CreateError("UnknownError", cause)

}
