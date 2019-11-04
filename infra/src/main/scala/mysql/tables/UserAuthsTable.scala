package infra.mysql.tables

import java.util.UUID
import models.user.{UserId, VerifiedUserId}

import scalikejdbc._
import skinny.orm.SkinnyCRUDMapperWithId

object UserAuthsTable extends SkinnyCRUDMapperWithId[UserId, UserAuthRecord] {
  override lazy val tableName           = "user_auths"
  override lazy val defaultAlias        = createAlias("ua")
  override lazy val primaryKeyFieldName = "user_id"

  override def extract(rs: WrappedResultSet, rn: ResultName[UserAuthRecord]): UserAuthRecord = autoConstruct(rs, rn)
  override def idToRawValue(userId: UserId)                                                  = userId.display
  override def rawValueToId(value: Any)                                                      = VerifiedUserId(UUID.fromString(value.toString))
  override def useExternalIdGenerator                                                        = true

  def columnsAndValues(record: UserAuthRecord) = List(
    Symbol("user_id")         -> record.user_id,
    Symbol("email")           -> record.email,
    Symbol("hashed_password") -> record.hashed_password
  )
}
