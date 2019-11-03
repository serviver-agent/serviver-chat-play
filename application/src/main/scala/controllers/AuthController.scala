package controllers

import javax.inject._
import play.api.mvc._

import play.api.i18n.{Lang, I18nSupport}

import models.{UserId, UserToken, UserTokenRepository}
import utils.FormUtils._
import utils.CirceUtils._
import io.circe.{Json, Encoder}
import io.circe.syntax._

@Singleton
class AuthController @Inject() (
    cc: ControllerComponents,
    userTokenRepository: UserTokenRepository
) extends AbstractController(cc)
    with I18nSupport {

  implicit val messages = messagesApi.preferred(Seq(Lang.defaultLang))

  import AuthController._

  def login() = Action { request =>
    bindFromRequest(AuthLoginRequest.form)(request).left
      .map(badForm => BadRequest(badForm.errorsAsJson))
      .map { form =>
        val userId: UserId = ??? // FIXMe
        val userToken      = userTokenRepository.create(userId)
        val response       = AuthLoginResponse(userToken)
        Ok(response.asJson)
      }
      .merge
  }

  def logout() = Action {
    InternalServerError
  }

}

object AuthController {

  case class AuthLoginRequest(email: String, rawPassword: String)
  object AuthLoginRequest {
    import play.api.data.Form
    import play.api.data.Forms._

    val form: Form[AuthLoginRequest] = Form(
      mapping(
        "email"       -> email,
        "rawPassword" -> nonEmptyText(minLength = 1, maxLength = 32)
      )(AuthLoginRequest.apply)(AuthLoginRequest.unapply)
    )
  }

  case class AuthLoginResponse(token: UserToken)
  implicit val userTokenEncoder: Encoder[UserToken] = Encoder[String].contramap(_.display)
  implicit val authLoginResponseEncoder: Encoder[AuthLoginResponse] = Encoder.instance { response =>
    Json.obj(
      "token" -> response.token.asJson
    )
  }

}
