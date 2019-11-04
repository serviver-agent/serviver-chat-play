package infra.mysql.tables

import java.util.UUID
import models.user.{UserId, VerifiedUserId}

import scalikejdbc._
import skinny.orm.SkinnyCRUDMapperWithId

object UserAuthsTable extends SkinnyCRUDMapperWithId[UserId, UserAuthRecord] {
  override lazy val tableName           = "UserAuths"
  override lazy val defaultAlias        = createAlias("ua")
  override lazy val primaryKeyFieldName = "id"

  override def extract(rs: WrappedResultSet, rn: ResultName[UserAuthRecord]): UserAuthRecord = autoConstruct(rs, rn)
  override def idToRawValue(userId: UserId)                                                  = userId.display
  override def rawValueToId(value: Any)                                                      = VerifiedUserId(UUID.fromString(value.toString))

  def columnsAndValues(record: UserAuthRecord) = List(
    Symbol("userId")         -> record.userId,
    Symbol("email")          -> record.email,
    Symbol("hashedPassword") -> record.hashedPassword
  )
}
