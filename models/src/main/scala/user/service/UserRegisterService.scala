package models.user.service

import javax.inject.{Singleton, Inject}

import models.user._
import models.user.repository._

@Singleton
class UserRegisterService @Inject() (
    userAuthRepository: UserAuthRepository,
    userInfoRepository: UserInfoRepository
) {

  import UserRegisterService._

  def create(request: UserCreateRequest): Either[Exception, Unit] = {
    val generatedUserId = GeneratedUserId.create()
    val userAuth        = UserAuth.createFromRawPassword(request.email, request.rawPassword)
    val userInfo        = UserInfo.create(request.userName, request.imageUrl)

    for {
      _ <- userAuthRepository.create(generatedUserId, userAuth)
      _ <- userInfoRepository.create(generatedUserId, userInfo)
    } yield ()
  }

}

object UserRegisterService {

  case class UserCreateRequest(
      userName: String,
      imageUrl: String,
      email: String,
      rawPassword: String
  )

}
