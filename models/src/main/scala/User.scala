package models

import java.util.UUID

import models.utils.ErrorUtils

// case class UserId(uuid: UUID) {
//   def display = uuid.toString
// }
// object UserId {
//   def createFromString(str: String): UserId = {
//     UserId(UUID.fromString(str))
//   }
// }

// case class User(id: UserId, name: String)
// object User {
//   def createFromName(name: String): User = {
//     val id = UserId(UUID.randomUUID())
//     User(id, name)
//   }
// }

// 命名をあとでUserする

trait UserId {
  def uuid: UUID
  final def display: String = uuid.toString
}
trait UnverifiedUserId extends UserId
object UnverifiedUserId {
  def fromString(userIdStr: String): UnverifiedUserId = {
    new UnverifiedUserId {
      override def uuid = UUID.fromString(userIdStr)
    }
  }
}
trait VerifiedUserId extends UserId

trait _UserId {
  def userId: UserId
}

// trait User

trait UserInfo {
  def name: UserInfo.UserName
  def imageUrl: UserInfo.UserImageUrl
}
object UserInfo {

  def create(userNameStr: String, imageUrlStr: String): Either[List[Exception], UserInfo] = {

    val maybeUserName: Either[List[Exception], UserName] = UserName.create(userNameStr)
    val maybeImageUrl: Either[List[Exception], UserImageUrl] = UserImageUrl.create(imageUrlStr)

    ErrorUtils.aggregateEitherLists2(
      (maybeUserName, maybeImageUrl),
      (n: UserName, i: UserImageUrl) => new UserInfo {
        override def name = n
        override def imageUrl = i
      }
    )

  }

  sealed abstract case class UserName(value: String)
  object UserName {
    val MaxLength = 32

    object ErrorDefs {
      val userNameLengthErrorOption: String => Option[Exception] = { userNameStr =>
        if (userNameStr.length <= MaxLength) None
        else Some(new Exception("32文字以下じゃないといけないやつ"))
      }

      val errDefs: List[String => Option[Exception]] = (userNameLengthErrorOption :: Nil)
    }

    private val createOnSuccess: String => UserName = userNameStr => new UserName(userNameStr) {}

    def create(userNameStr: String): Either[List[Exception], UserName] = {
      ErrorUtils.collectErrors(userNameStr, ErrorDefs.errDefs, createOnSuccess)
    }
  }

  sealed abstract case class UserImageUrl(value: String)
  object UserImageUrl {
    import scala.util.matching.Regex
    val MaxLength = 512
    val UrlPattern = """^(http|https)://([\w-]+\.)+[\w-]+(/[\w-./?%&=]*)?$""".r

    object ErrorDefs {
      val urlLengthErrorOption: String => Option[Exception] = { imageUrlStr =>
        if (imageUrlStr.length <= MaxLength) None
        else Some(new Exception("512文字以下じゃないといけないやつ"))
      }
      val urlErrorOption: String => Option[Exception] = { imageUrlStr =>
        if (UrlPattern.matches(imageUrlStr)) None
        else Some(new Exception("URLの形式がダメなやつ"))
      }

      val errDefs: List[String => Option[Exception]] = (urlLengthErrorOption :: urlErrorOption :: Nil)
    }

    private val createOnSuccess: String => UserImageUrl = imageUrlStr => new UserImageUrl(imageUrlStr) {}

    def create(imageUrlStr: String): Either[List[Exception], UserImageUrl] = {
      ErrorUtils.collectErrors(imageUrlStr, ErrorDefs.errDefs, createOnSuccess)
    }
  }
}
trait _UserInfo {
  def userInfo: UserInfo
}

trait UserAuthentication {
  def email: String
  def hashedPassword: String
}
trait _UserAuthentication {
  def userAuthentication: UserAuthentication
}

trait UserToken {
  def value: String
}
trait _UserToken {
  def userToken: UserToken
}

trait UserAll
  extends _UserInfo
  with _UserAuthentication

object UserAll {

}

object Hoge {

  val hoge: User with _UserInfo = new User with _UserInfo {
    val userId = new UserId {
      val uuid = UUID.randomUUID()
    }
    val userInfo = new UserInfo {
      val name = "みやしー"
      val imageUrl = "riho.jpg"
    }
  }

}

trait UserInfoRepository {
  def save(): Unit
}
trait UserAuthRepository {
  def save(): Unit
}

trait UserRegisterServive {

  def userInfoRepository: UserInfoRepository
  def userAuthRepository: UserAuthRepository

  def create(request: UserCreateRequest): Unit = {

  }

}
object UserRegisterServive {
  class UserCreateRequest(
    userName: String,
    email: String,
    rawPassword: String
  )
}

trait UserAuthenticationService {
  def authenticate(userAuth: UserAuthentication): UserId
}

trait UserTokenService {

  def create(userId: UserId): UserToken
  def findBy(userToken: UserToken): Option[UserId]
  def delete(userToken: UserToken): Unit

}

trait UserInfoService {

  def findBy(userId: UserId): UserId with UserInfo

}
