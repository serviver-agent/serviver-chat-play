package models.user.service

import models.user._

trait UserAuthenticationService {
  def authenticate(userAuth: UserAuth): UserId
}
