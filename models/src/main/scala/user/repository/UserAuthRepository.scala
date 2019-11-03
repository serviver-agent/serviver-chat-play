package models.user.repository

import models.user._

trait UserAuthRepository {

  def findBy(userId: UnverifiedUserId): Option[(VerifiedUserId, UserAuth)]
  def findBy(userAuth: UserAuth): Option[VerifiedUserId]
  def create(userId: GeneratedUserId, userAuth: UserAuth): Either[Exception, Unit]

}
