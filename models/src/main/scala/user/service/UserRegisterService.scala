package models.user.service

import javax.inject.{Singleton, Inject}

import models.user._
import models.user.repository._
import models.utils.ErrorUtils._

@Singleton
class UserRegisterServive @Inject() (
  userAuthRepository: UserAuthRepository,
  userInfoRepository: UserInfoRepository
) {

  import UserRegisterServive._

  def create(request: UserCreateRequest): Either[List[Exception], Unit] = {
    val generatedUserId = GeneratedUserId.create()
    val stillUnvalidatedUserAuth = UserAuth.create(request.email, request.rawPassword)
    val stillUnvalidatedUserInfo = UserInfo.create(request.userName, request.imageUrl)
    aggregateEitherLists2[UserAuth, UserInfo, (UserAuth, UserInfo), Exception](
      (stillUnvalidatedUserAuth, stillUnvalidatedUserInfo),
      (a: UserAuth, b: UserInfo) => (a, b)
    ).flatMap { case (userAuth, userInfo) =>
      def a: Either[Exception, Unit] = userAuthRepository.create(generatedUserId, userAuth)
      def b: Either[Exception, Unit] = userInfoRepository.create(generatedUserId, userInfo)
      (for {
        _ <- userAuthRepository.create(generatedUserId, userAuth)
        _ <- userInfoRepository.create(generatedUserId, userInfo)
      } yield ()).left.map(_ :: Nil)
    }
  }

}

object UserRegisterServive {

  case class UserCreateRequest(
    userName: String,
    imageUrl: String,
    email: String,
    rawPassword: String
  )

}
