package utils

object OptionUtils {

  implicit class MyOption[T](self: Option[T]) {
    def toEither[E](left: E): Either[E, T] = {
      self match {
        case Some(value) => Right(value)
        case None        => Left(left)
      }
    }
  }

}
