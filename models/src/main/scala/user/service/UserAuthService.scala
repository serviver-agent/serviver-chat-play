package models.user.service

import javax.inject.{Singleton, Inject}

import models.user._
import models.user.repository._

@Singleton
class UserAuthService @Inject() (
    repository: UserAuthRepository
) {

  def authenticate(userAuth: UserAuth): Option[VerifiedUserId] = repository.findBy(userAuth)

}
