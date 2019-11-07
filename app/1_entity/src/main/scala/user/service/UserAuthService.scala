package entity.user.service

import javax.inject.{Singleton, Inject}

import entity.user._
import entity.user.repository._

@Singleton
class UserAuthService @Inject() (
    repository: UserAuthRepository
) {

  def authenticate(userAuth: UserAuth): Option[VerifiedUserId] = repository.findBy(userAuth)

}
