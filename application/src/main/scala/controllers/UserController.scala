package controllers

import javax.inject._
import play.api.mvc._
import play.api.i18n.{Lang, I18nSupport}

import models.user._
import models.user.service._
import models.user.service.UserRegisterService.UserCreateRequest

import utils.FormUtils._
import utils.CirceUtils._
import io.circe.generic.auto._
import io.circe.syntax._

@Singleton
class UserController @Inject() (
    cc: ControllerComponents,
    userInfoService: UserInfoService,
    userRegisterService: UserRegisterService
) extends AbstractController(cc)
    with I18nSupport {

  implicit val messages = messagesApi.preferred(Seq(Lang.defaultLang))

  import UserController._

  def findAll() = Action {
    val users: List[(VerifiedUserId, UserInfo)] = userInfoService.findAll()
    val response: FindAllResponse               = users.map(FindUserResponse.fromAttrs)
    Ok(response.asJson)
  }

  def findBy(id: String) = Action {
    val unverifiedUserid = UnverifiedUserId.fromString(id)
    userInfoService.findBy(unverifiedUserid) match {
      case None => NotFound
      case Some(user) => {
        val response = FindUserResponse.fromAttrs(user)
        Ok(response.asJson)
      }
    }
  }

  def create() = Action { httpRequest =>
    val e = for {
      userCreateRequest <- bindFromRequest(createUserForm)(httpRequest).left.map { badForm =>
        BadRequest(badForm.errorsAsJson)
      }
      _ <- userRegisterService.create(userCreateRequest).left.map { _ =>
        InternalServerError
      }
    } yield Ok
    e.merge
  }

}

object UserController {

  type FindAllResponse = List[FindUserResponse]

  case class FindUserResponse(
      id: String,
      userName: String,
      imageUrl: String
  )
  object FindUserResponse {
    def fromAttrs(user: (VerifiedUserId, UserInfo)) = {
      val (userId, userInfo) = user
      FindUserResponse(
        userId.display,
        userInfo.userName.display,
        userInfo.imageUrl.display
      )
    }
  }

  import play.api.data.Form
  import play.api.data.Forms._

  val createUserForm: Form[UserCreateRequest] = Form(
    mapping(
      "userName"    -> nonEmptyText(minLength = 1, maxLength = 32),
      "imageUrl"    -> nonEmptyText(minLength = 1, maxLength = 512),
      "email"       -> email,
      "rawPassword" -> nonEmptyText(minLength = 8, maxLength = 64)
    )(UserCreateRequest.apply)(UserCreateRequest.unapply)
  )

}
