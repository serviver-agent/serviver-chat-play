package models.user.repository

import models.user._

trait UserAuthRepository {

  def findBy(userId: UserId): Option[VerifiedUserId with UserAuth]
  def create(userId: GeneratedUserId, userAuth: UserAuth): Either[Exception, Unit]

}
