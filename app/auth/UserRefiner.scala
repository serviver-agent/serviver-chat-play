package auth

import javax.inject.Inject
import play.api.mvc.{Request, Result, ActionRefiner}
import scala.concurrent.{Future, ExecutionContext}
import models.{UserToken, UserTokenRepository}

class UserRefiner @Inject() (
    ec: ExecutionContext,
    userTokenRepository: UserTokenRepository
) extends ActionRefiner[Request, UserRequest] {

  implicit protected override def executionContext = ec
  protected def refine[A](request: Request[A]): Future[Either[Result, UserRequest[A]]] = {
    request.headers.get("Authorization") match {
      case None => Future.successful(Left(UserRefiner.Unauthorized))
      case Some(token) => {
        val userToken = UserToken(token)
        Future {
          userTokenRepository.findBy(userToken) match {
            case None         => Left(UserRefiner.Unauthorized)
            case Some(userId) => Right(new UserRequest(userId, request))
          }
        }
      }
    }
  }

}

object UserRefiner {

  val Unauthorized = {
    import play.api.mvc.ResponseHeader
    import play.api.http.HttpEntity
    import akka.util.ByteString

    Result(
      ResponseHeader(401),
      HttpEntity.Strict(ByteString(""), Some("application/json"))
    )
  }

}
