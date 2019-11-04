package models.user.repository

import models.user._

trait UserInfoRepository {

  import UserInfoRepository._

  def findAll(): List[(VerifiedUserId, UserInfo)]
  def findBy(userId: UnverifiedUserId): Option[(VerifiedUserId, UserInfo)]
  def create(userId: GeneratedUserId, userInfo: UserInfo): Either[CreateError, VerifiedUserId]
  def update(userId: VerifiedUserId, userInfo: UserInfo): Unit

}

object UserInfoRepository {

  abstract class CreateError(message: String, cause: Throwable = new Throwable) extends Exception(message, cause)
  case class DuplicateUserError(cause: Throwable)                               extends CreateError("DuplicateUserError", cause)
  case class UnknownError(cause: Throwable)                                     extends CreateError("UnknownError", cause)

}
