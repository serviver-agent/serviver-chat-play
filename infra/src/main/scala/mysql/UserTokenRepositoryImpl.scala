package infra.mysql

import models.user._
import models.user.repository.UserTokenRepository

import infra.mysql.tables.{UserTokenRecord, UserTokensTable}
import scalikejdbc._

class UserTokenRepositoryImpl extends UserTokenRepository {

  override def findBy(userToken: UnverifiedUserToken): Option[VerifiedUserId] = {
    UserTokensTable.findById(userToken).map(_.userIdToEntity)
  }

  override def create(userId: VerifiedUserId): VerifiedUserToken = {
    val record     = UserTokenRecord.createWithRandomToken(userId)
    val parameters = UserTokensTable.columnsAndValues(record)
    UserTokensTable.createWithAttributes(parameters: _*)
    VerifiedUserToken(record.userToken)
  }

  override def delete(userToken: UserToken): Unit = {
    UserTokensTable.deleteById(userToken)
  }

  override def deleteBy(userId: VerifiedUserId): Unit = {
    UserTokensTable.deleteBy(sqls.eq(UserTokensTable.column.userId, userId.display))
  }

}
