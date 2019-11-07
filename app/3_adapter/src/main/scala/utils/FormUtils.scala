package application.utils

import play.api.mvc._
import play.api.data._

object FormUtils {

  def bindFromRequest[T](form: Form[T])(implicit request: Request[_]): Either[Form[T], T] = {

    val bound = form.bindFromRequest()
    bound.value match {
      case Some(v) if form.errors.isEmpty => Right(v)
      case _                              => Left(bound)
    }
  }

}
