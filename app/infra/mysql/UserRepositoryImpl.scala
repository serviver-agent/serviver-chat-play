package infra.mysql

import models.{User, UserId, UserRepository}
import scalikejdbc._

class UserRepositoryImpl extends UserRepository {

  import UserRepositoryImpl._

  val u = UsersTable.syntax

  def findAll(): List[User] = {
    val records = DB.readOnly { implicit session =>
      withSQL {
        select(u.result.*).from(UsersTable as u)
      }.map(UserRecord(u.resultName)).list.apply()
    }
    records.map(_.toEntity)
  }

  def findBy(userId: UserId): Option[User] = {
    val id = userId.display
    val records = DB.readOnly { implicit session =>
      withSQL {
        select(u.result.*).from(UsersTable as u)
          .where.eq(u.id, id)
      }.map(UserRecord(u.resultName)).single.apply()
    }
    records.map(_.toEntity)
  }

  def create(user: User) = {
    val record = UserRecord.fromEntity(user)
    DB.localTx { implicit session =>
      withSQL {
        insert.into(UsersTable).namedValues(record.columnsAndValues: _*)
      }.update.apply()
    }
  }

}

object UserRepositoryImpl {

  case class UserRecord(id: String, name: String) {
    import java.util.UUID
    def toEntity = {
      User(UserId(UUID.fromString(id)), name)
    }
    def columnsAndValues = List(
      UsersTable.syntax.id   -> this.id,
      UsersTable.syntax.name -> this.name
    )
  }

  object UserRecord {
    def fromEntity(entity: User): UserRecord = {
      UserRecord(entity.id.display, entity.name)
    }

    def apply(c: SyntaxProvider[UserRecord])(rs: WrappedResultSet): UserRecord = {
      apply(c.resultName)(rs)
    }
    def apply(c: ResultName[UserRecord])(rs: WrappedResultSet): UserRecord = {
      new UserRecord(
        rs.get(c.id),
        rs.get(c.name)
      )
    }
  }

  object UsersTable extends SQLSyntaxSupport[UserRecord] {
    override val tableName = "users"
  }

}
