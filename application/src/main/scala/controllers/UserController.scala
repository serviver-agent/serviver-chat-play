package controllers

import javax.inject._
import play.api.mvc._

import models.{User, UserId, UserRepository}
import models.user.service.UserRegisterServive
import models.user.service.UserRegisterServive.UserCreateRequest

import utils.FormUtils._
import utils.CirceUtils._
import io.circe.{Json, Encoder}
import io.circe.syntax._

@Singleton
class UserController @Inject() (
    cc: ControllerComponents,
    userRepository: UserRepository,
    userRegisterService: UserRegisterServive
) extends AbstractController(cc) {

  import UserController._

  def all() = Action {
    val users: List[User] = userRepository.findAll()
    Ok(users.asJson)
  }

  def show(id: String) = Action {
    val userId = UserId.createFromString(id)
    userRepository.findBy(userId) match {
      case None       => NotFound
      case Some(user) => Ok(user.asJson)
    }
  }

  def create() = Action { request =>
    bindFromRequest(CreateUserForm.form)(request).left
      .map(_ => BadRequest)
      .map { form =>
        val user = User.createFromName(form.name)
        userRepository.create(user)
        Ok
      }
      .merge
  }

  def create2() = Action { httpRequest =>
    bindFromRequest(CreateUserForm.form)(httpRequest).left
      .map(_ => BadRequest)
      .map { request =>
        userRegisterService.create(request) match {
          case Left(es) => ??? // ここでどうやって出力するのか迷うから意味ねえって気がしてきた
          case Right(_) => Ok
        }
      }
      .merge
  }

}

object UserController {

  implicit val userIdEncoder: Encoder[UserId] = Encoder[String].contramap(_.display)
  implicit val userEncoder: Encoder[User] = Encoder.instance { user =>
    Json.obj(
      "id"   -> user.id.asJson,
      "name" -> user.name.asJson
    )
  }

  case class CreateUserForm(name: String)
  object CreateUserForm {
    import play.api.data.Form
    import play.api.data.Forms._

    val form: Form[CreateUserForm] = Form(
      mapping(
        "name" -> nonEmptyText(minLength = 1, maxLength = 32)
      )(CreateUserForm.apply)(CreateUserForm.unapply)
    )
  }

  val userCreateForm: Form[UserCreateRequest] = {
    import models.user.UserInfo.{MinLength, MaxLength}

    form(
      mapping(
        "userName"    -> nonEmptyText(minLength = MinLength, maxLength = MaxLength),
        "imageUrl"    -> nomEmptyText,
        "email"       -> email,
        "rawPassword" -> nomEmptyText
      )(UserCreateRequest.apply)(UserCreateRequest.unapply)
    )
  }

}
