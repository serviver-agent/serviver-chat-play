package infra.mysql

import entity.user.repository.UserAuthRepository
import entity.user._

import infra.mysql.tables.{UserAuthRecord, UserAuthsTable}
import scalikejdbc._

import scala.util.Try

class UserAuthRepositoryImpl extends UserAuthRepository {

  import UserAuthRepository._

  override def findBy(userId: UnverifiedUserId): Option[(VerifiedUserId, UserAuth)] = {
    UserAuthsTable.findById(userId).map(_.toEntity)
  }

  override def findBy(userAuth: UserAuth): Option[VerifiedUserId] = {
    UserAuthsTable
      .findBy(
        sqls
          .eq(UserAuthsTable.column.email, userAuth.email.display)
          .and
          .eq(UserAuthsTable.column.hashed_password, userAuth.hashedPassword.display)
      )
      .map(_.userIdToEntity)
  }

  override def create(userId: GeneratedUserId, userAuth: UserAuth): Either[CreateError, VerifiedUserId] = {
    import java.sql.SQLIntegrityConstraintViolationException

    Try {
      val record     = UserAuthRecord.fromEntity(userId, userAuth)
      val parameters = UserAuthsTable.columnsAndValues(record)
      UserAuthsTable.createWithAttributes(parameters: _*)
      VerifiedUserId(userId.value)
    }.toEither.left
      .map {
        case e: SQLIntegrityConstraintViolationException => DuplicateUserError(e)
        case e                                           => UnknownError(e)
      }
  }

}
