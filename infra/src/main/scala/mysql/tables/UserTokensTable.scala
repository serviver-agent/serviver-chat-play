package infra.mysql.tables

import models.user.{UserToken, VerifiedUserToken}

import scalikejdbc._
import skinny.orm.SkinnyCRUDMapperWithId

object UserTokensTable extends SkinnyCRUDMapperWithId[UserToken, UserTokenRecord] {
  override lazy val tableName           = "Channels"
  override lazy val defaultAlias        = createAlias("c")
  override lazy val primaryKeyFieldName = "id"

  override def extract(rs: WrappedResultSet, rn: ResultName[UserTokenRecord]): UserTokenRecord = autoConstruct(rs, rn)
  override def idToRawValue(userToken: UserToken)                                              = userToken.display
  override def rawValueToId(value: Any)                                                        = VerifiedUserToken(value.toString)

  def columnsAndValues(record: UserTokenRecord) = List(
    Symbol("userToken") -> record.userToken,
    Symbol("userId")    -> record.userId
  )
}
