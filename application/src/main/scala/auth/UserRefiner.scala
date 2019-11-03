package application.auth

import javax.inject.Inject

import play.api.mvc.{Request, Result, ActionRefiner}
import scala.concurrent.{Future, ExecutionContext}

import models.user.{UnverifiedUserToken, VerifiedUserId}
import models.user.service.UserTokenService

import play.api.mvc.Results

import application.utils.OptionUtils.MyOption

class UserRefiner @Inject() (
    ec: ExecutionContext,
    userTokenService: UserTokenService
) extends ActionRefiner[Request, UserRequest] {

  import UserRefiner._

  implicit override protected def executionContext = ec

  override protected def refine[A](request: Request[A]): Future[Either[Result, UserRequest[A]]] = Future.successful {
    for {
      unverifiedUserToken <- request.headers.get("Authorization").toEither(BadRequest)
      verifiedUserId <- userTokenService
        .findBy(UnverifiedUserToken.fromString(unverifiedUserToken))
        .toEither(Unauthorized)
    } yield new UserRequest(verifiedUserId, request)
  }

}

object UserRefiner {

  val BadRequest = {
    import play.api.mvc.ResponseHeader
    import play.api.http.HttpEntity
    import akka.util.ByteString

    Result(
      ResponseHeader(400),
      HttpEntity.Strict(ByteString("Token not found in header Authorization."), Some("application/json"))
    )
  }

  val Unauthorized = {
    import play.api.mvc.ResponseHeader
    import play.api.http.HttpEntity
    import akka.util.ByteString

    Result(
      ResponseHeader(401),
      HttpEntity.Strict(ByteString("Invalid Token"), Some("application/json"))
    )
  }

}
