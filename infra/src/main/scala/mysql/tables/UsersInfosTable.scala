package infra.mysql.tables

import java.util.UUID
import models.user.{UserId, VerifiedUserId}

import scalikejdbc._
import skinny.orm.SkinnyCRUDMapperWithId

object UserInfosTable extends SkinnyCRUDMapperWithId[UserId, UserInfoRecord] {
  override lazy val tableName           = "UserInfos"
  override lazy val defaultAlias        = createAlias("ui")
  override lazy val primaryKeyFieldName = "id"

  override def extract(rs: WrappedResultSet, rn: ResultName[UserInfoRecord]): UserInfoRecord = autoConstruct(rs, rn)
  override def idToRawValue(userId: UserId)                                                  = userId.display
  override def rawValueToId(value: Any)                                                      = VerifiedUserId(UUID.fromString(value.toString))
  override def useExternalIdGenerator                                                        = true

  def columnsAndValues(record: UserInfoRecord) = List(
    Symbol("userId")   -> record.userId,
    Symbol("userName") -> record.userName,
    Symbol("imageUrl") -> record.imageUrl
  )

  def updateAttributes(record: UserInfoRecord) = List(
    Symbol("userName") -> record.userName,
    Symbol("imageUrl") -> record.imageUrl
  )
}
