package application.auth

import play.api.mvc.{Request, WrappedRequest}
import entity.user.VerifiedUserId

class UserRequest[A](val userId: VerifiedUserId, request: Request[A]) extends WrappedRequest[A](request)
