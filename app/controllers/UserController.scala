package controllers

import javax.inject._
import play.api.mvc._

import models.{User, UserId, UserRepository}
import utils.FormUtils._
import utils.CirceUtils._
import io.circe.{Json, Encoder}
import io.circe.syntax._

@Singleton
class UserController @Inject() (
    cc: ControllerComponents,
    userRepository: UserRepository
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

}
