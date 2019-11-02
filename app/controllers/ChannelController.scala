
package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class ChannelController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def all() = Action {
    InternalServerError
  }

  def show(id: String) = Action {
    InternalServerError
  }

  def create = Action {
    InternalServerError
  }

}
