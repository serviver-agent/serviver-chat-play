package models.user

import models.utils.ErrorUtils

trait UserInfo {
  def name: UserInfo.UserName
  def imageUrl: UserInfo.UserImageUrl
}

trait _UserInfo {
  def userInfo: UserInfo
}

object UserInfo {

  def create(userNameStr: String, imageUrlStr: String): Either[List[Exception], UserInfo] = {

    val maybeUserName: Either[List[Exception], UserName]     = UserName.create(userNameStr)
    val maybeImageUrl: Either[List[Exception], UserImageUrl] = UserImageUrl.create(imageUrlStr)

    ErrorUtils.aggregateEitherLists2(
      (maybeUserName, maybeImageUrl),
      (n: UserName, i: UserImageUrl) =>
        new UserInfo {
          override def name     = n
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
    val MaxLength  = 512
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
