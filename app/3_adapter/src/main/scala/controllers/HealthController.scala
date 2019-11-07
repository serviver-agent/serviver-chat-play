package application.controllers

import javax.inject._
import play.api.mvc._

import application.utils.HealthChecker

@Singleton
class HealthController @Inject() (
    cc: ControllerComponents,
    healthChecker: HealthChecker
) extends AbstractController(cc) {

  def check() = Action {

    if (healthChecker.check()) Ok
    else InternalServerError

    Ok

  }

}
