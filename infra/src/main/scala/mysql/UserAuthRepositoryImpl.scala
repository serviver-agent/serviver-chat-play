package infra.mysql

import models.user.repository.UserAuthRepository
import models.user._

import infra.mysql.tables.{UserAuthRecord, UserAuthsTable}
import scalikejdbc._

class UserAuthRepositoryImpl extends UserAuthRepository {

  override def findBy(userId: UnverifiedUserId): Option[(VerifiedUserId, UserAuth)] = {
    UserAuthsTable.findById(userId).map(_.toEntity)
  }

  override def findBy(userAuth: UserAuth): Option[VerifiedUserId] = {
    UserAuthsTable
      .findBy(
        sqls
          .eq(UserAuthsTable.column.email, userAuth.email.display)
          .and
          .eq(UserAuthsTable.column.hashedPassword, userAuth.hashedPassword.display)
      )
      .map(_.userIdToEntity)
  }

  override def create(userId: GeneratedUserId, userAuth: UserAuth): Either[Exception, Unit] = {
    val record     = UserAuthRecord.fromEntity(userId, userAuth)
    val parameters = UserAuthsTable.columnsAndValues(record)
    UserAuthsTable.createWithAttributes(parameters: _*)
    Right()
  }

}
