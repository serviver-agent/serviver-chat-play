package infra.mysql.tables

import scalikejdbc._
import skinny.orm.SkinnyCRUDMapper

object UserInfosTable extends SkinnyCRUDMapper[UserInfoRecord] {
  override lazy val tableName = "UserInfos"
  override lazy val defaultAlias = createAlias("ui")
  override def extract(rs: WrappedResultSet, rn: ResultName[UserInfoRecord]): UserInfoRecord = autoConstruct(rs, rn)
}
