package entity.message

import java.util.UUID
import entity.user.VerifiedUserId

case class MessageId(uuid: UUID) {
  def display = uuid.toString
}
object MessageId {
  def createFromString(str: String): MessageId = {
    MessageId(UUID.fromString(str))
  }
}

case class Message(id: MessageId, userId: VerifiedUserId, body: MessageBody)
object Message {
  def create(userId: VerifiedUserId, rawBody: String): Either[MessageError, Message] = {
    val id         = MessageId(UUID.randomUUID())
    val bodyEither = MessageBody.create(rawBody)
    bodyEither.map { body =>
      Message(id, userId, body)
    }
  }
}

case class MessageBody(value: String)
object MessageBody {
  def create(str: String): Either[MessageError, MessageBody] = {
    if (str.length <= 255) Right(MessageBody(str))
    else Left(MessageBodyError)
  }
}

trait MessageError      extends Exception
object MessageBodyError extends MessageError
