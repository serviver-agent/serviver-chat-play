package infra.mysql.tables

import models.user.{UserToken, VerifiedUserToken}

import scalikejdbc._
import skinny.orm.SkinnyCRUDMapperWithId

object UserTokensTable extends SkinnyCRUDMapperWithId[UserToken, UserTokenRecord] {
  override lazy val tableName           = "user_tokens"
  override lazy val defaultAlias        = createAlias("ut")
  override lazy val primaryKeyFieldName = "user_token"

  override def extract(rs: WrappedResultSet, rn: ResultName[UserTokenRecord]): UserTokenRecord = autoConstruct(rs, rn)
  override def idToRawValue(userToken: UserToken)                                              = userToken.display
  override def rawValueToId(value: Any)                                                        = VerifiedUserToken(value.toString)
  override def useExternalIdGenerator                                                          = true

  def columnsAndValues(record: UserTokenRecord) = List(
    Symbol("userToken") -> record.user_token,
    Symbol("userId")    -> record.user_id
  )
}
