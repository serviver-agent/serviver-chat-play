package application.controllers

import javax.inject._
import play.api.mvc._

@Singleton
class MessageController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def getMessages(channelId: String) = Action {
    InternalServerError
  }

  def post(channelId: String) = Action {
    InternalServerError
  }

}
