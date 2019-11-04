package infra.mysql.tables

import scalikejdbc._
import skinny.orm.SkinnyCRUDMapper

object UserAuthsTable extends SkinnyCRUDMapper[UserAuthRecord] {
  override lazy val tableName = "UserAuths"
  override lazy val defaultAlias = createAlias("ua")
  override def extract(rs: WrappedResultSet, rn: ResultName[UserAuthRecord]): UserAuthRecord = autoConstruct(rs, rn)
}
