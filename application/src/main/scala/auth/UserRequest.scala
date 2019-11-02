package auth

import play.api.mvc.{Request, WrappedRequest}
import models.UserId

class UserRequest[A](val user: UserId, request: Request[A]) extends WrappedRequest[A](request)
