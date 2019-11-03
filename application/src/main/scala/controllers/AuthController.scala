package controllers

import javax.inject._
import play.api.mvc._

import play.api.i18n.{Lang, I18nSupport}

import models.user.{UserAuth, UserToken}
import models.user.service.{UserAuthService, UserTokenService}
import auth.UserRefiner

import utils.FormUtils._
import utils.CirceUtils._
import utils.OptionUtils.MyOption
import io.circe.{Json, Encoder}
import io.circe.generic.auto._
import io.circe.syntax._

@Singleton
class AuthController @Inject() (
    cc: ControllerComponents,
    userAuthService: UserAuthService,
    userTokenService: UserTokenService,
    userRefiner: UserRefiner
) extends AbstractController(cc)
    with I18nSupport {

  implicit val messages = messagesApi.preferred(Seq(Lang.defaultLang))

  import AuthController._

  def login() = Action { httpRequest =>
    val e = for {
      userAuth <- bindFromRequest(authLoginForm)(httpRequest).left.map { badForm =>
        BadRequest(badForm.errorsAsJson)
      }
      verifiedUserId <- userAuthService.authenticate(userAuth).toEither(Unauthorized)
    } yield {
      val verifiedUserToken = userTokenService.create(verifiedUserId)
      val response          = AuthLoginResponse.fromToken(verifiedUserToken)
      Ok(response.asJson)
    }
    e.merge
  }

  def logout() = cc.actionBuilder.andThen(userRefiner) { userRequest =>
    userTokenService.deleteBy(userRequest.userId)
    Ok
  }

}

object AuthController {

  import play.api.data.Form
  import play.api.data.Forms._

  val authLoginForm: Form[UserAuth] = Form(
    mapping(
      "email"       -> email,
      "rawPassword" -> nonEmptyText(minLength = 8, maxLength = 32)
    )(UserAuth.createFromRawPassword)(UserAuth.passwordHiddenUnapply)
  )

  case class AuthLoginResponse(token: String)
  object AuthLoginResponse {
    def fromToken(token: UserToken) = AuthLoginResponse(token.display)
  }

}
