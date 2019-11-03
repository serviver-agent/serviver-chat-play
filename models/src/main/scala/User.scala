package models

import java.util.UUID

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
  def name: UserName
  def imageUrl: UserImageUrl
}
object UserInfo {
  def create(userNameStr: String): Either[Exception, UserName] = {
    UserName.create(userNameStr).map { userName =>
      new UserInfo {
        val name = userName
      }
    }
  }

  sealed abstract case class UserName(value: String)
  object UserName {
    val MaxLength = 32
    def create(userNameStr: String): Either[List[Exception], UserName] = {
      if (userNameStr.length > MaxLength) Left(new Exception("32文字以下じゃないといけないやつ") :: Nil)
      else UserName(userNameStr)
      // fixme
    }
  }

  sealed abstract case class UserImageUrl(value: String)
  object UserImageUrl {
    import scala.util.matching.Regex
    val MaxLength = 512
    val urlPattern = """^(http|https)://([\w-]+\.)+[\w-]+(/[\w-./?%&=]*)?$""".r

    def create(imageUrlStr: String): Either[List[Exception], UserName] = {
      val urlLengthErrorOption: Option[Exception] = {
        if (imageUrlStr.length <= MaxLength) None
        else Some(new Exception("512文字以下じゃないといけないやつ"))
      }
      val urlErrorOption: Option[Exception] = {
        if (urlPattern.matches(imageUrlStr)) None
        else Some(new Exception("URLの形式がダメなやつ"))
      }

      val os: List[Exception] = (urlLengthErrorOption :: urlErrorOption :: Nil).flatMap {
        case Some(error) => List(error)
        case None => Nil
      }
      val res: Either[List[Throwable, UserImageUrl]] = os match {
        case es :: Nil => Left(es)
        case Nil => Right(UserImageUrl(imageUrlStr))
      }
      res
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
