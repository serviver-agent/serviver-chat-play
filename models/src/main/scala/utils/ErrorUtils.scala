package models.utils

object ErrorUtils {

  def collectErrors[A, B, E](
      rawValue: A,
      errDefs: List[A => Option[E]],
      onSuccess: A => B
  ): Either[List[E], B] = {
    errDefs
      .map(_.apply(rawValue))
      .flatMap {
        case Some(error) => List(error)
        case None        => Nil
      } match {
      case Nil => Right(onSuccess(rawValue))
      case es  => Left(es)
    }
  }

  def aggregateEitherLists2[A1, A2, B, E](
      eitherLists: (Either[List[E], A1], Either[List[E], A2]),
      onSuccess: (A1, A2) => B
  ): Either[List[E], B] = {
    eitherLists match {
      case (Right(a1), Right(a2)) => Right(onSuccess(a1, a2))
      case (a1, a2) => {
        val eithers = a1 :: a2 :: Nil
        val errors = eithers.flatMap {
          case Left(e)  => e
          case Right(_) => Nil
        }
        Left(errors)
      }
    }
  }

}
