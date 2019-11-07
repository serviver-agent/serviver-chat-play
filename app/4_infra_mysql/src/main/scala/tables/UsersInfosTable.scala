package infra.mysql.tables

import java.util.UUID
import models.user.{UserId, VerifiedUserId}

import scalikejdbc._
import skinny.orm.SkinnyCRUDMapperWithId

object UserInfosTable extends SkinnyCRUDMapperWithId[UserId, UserInfoRecord] {
  override lazy val tableName           = "user_infos"
  override lazy val defaultAlias        = createAlias("ui")
  override lazy val primaryKeyFieldName = "user_id"

  override def extract(rs: WrappedResultSet, rn: ResultName[UserInfoRecord]): UserInfoRecord = autoConstruct(rs, rn)
  override def idToRawValue(userId: UserId)                                                  = userId.display
  override def rawValueToId(value: Any)                                                      = VerifiedUserId(UUID.fromString(value.toString))
  override def useExternalIdGenerator                                                        = true

  def columnsAndValues(record: UserInfoRecord) = List(
    Symbol("user_id")   -> record.user_id,
    Symbol("user_name") -> record.user_name,
    Symbol("image_url") -> record.image_url
  )

  def updateAttributes(record: UserInfoRecord) = List(
    Symbol("user_name") -> record.user_name,
    Symbol("image_url") -> record.image_url
  )
}
