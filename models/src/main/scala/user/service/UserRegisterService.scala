package models.user.service

import models.user._
import models.user.repository._

trait UserRegisterServive {

  import UserRegisterServive._

  def userInfoRepository: UserInfoRepository
  def userAuthRepository: UserAuthRepository

  def create(request: UserCreateRequest): Unit

}

object UserRegisterServive {

  class UserCreateRequest(
    userName: String,
    email: String,
    rawPassword: String
  )
  
}
