
package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class UserController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {

  def create() = Action {
    InternalServerError
  }

  def show(id: String) = Action {
    InternalServerError
  }

}
