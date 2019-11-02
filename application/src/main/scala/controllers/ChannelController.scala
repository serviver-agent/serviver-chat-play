package controllers

import javax.inject._
import play.api.mvc._

import models.{Channel, ChannelId, ChannelRepository}
import utils.FormUtils._
import utils.CirceUtils._
import io.circe.{Json, Encoder}
import io.circe.syntax._

@Singleton
class ChannelController @Inject() (
    cc: ControllerComponents,
    channelRepository: ChannelRepository
) extends AbstractController(cc) {

  import ChannelController._

  def all() = Action {
    val channels: List[Channel] = channelRepository.findAll()
    Ok(channels.asJson)
  }

  def show(id: String) = Action {
    val channelId = ChannelId.createFromString(id)
    channelRepository.findBy(channelId) match {
      case None          => NotFound
      case Some(channel) => Ok(channel.asJson)
    }
  }

  def create = Action { request =>
    bindFromRequest(CreateChannelForm.form)(request).left
      .map(_ => BadRequest)
      .map { form =>
        val channel = Channel.createFromName(form.name)
        channelRepository.create(channel)
        Ok
      }
      .merge
  }

}

object ChannelController {

  implicit val channelIdEncoder: Encoder[ChannelId] = Encoder[String].contramap(_.display)
  implicit val channelEncoder: Encoder[Channel] = Encoder.instance { user =>
    Json.obj(
      "id"   -> user.id.asJson,
      "name" -> user.name.asJson
    )
  }

  case class CreateChannelForm(name: String)
  object CreateChannelForm {
    import play.api.data.Form
    import play.api.data.Forms._

    val form: Form[CreateChannelForm] = Form(
      mapping(
        "name" -> nonEmptyText(minLength = 1, maxLength = 32)
      )(CreateChannelForm.apply)(CreateChannelForm.unapply)
    )
  }

}
