package services

import javax.inject.{Singleton, Inject}
import models.{UserId, UserToken, UserTokenRepository}

@Singleton
class UserTokenService @Inject() (
    userAuthRepository: UserTokenRepository
) {

  def register(userId: UserId): UserToken = {
    
  }

}
