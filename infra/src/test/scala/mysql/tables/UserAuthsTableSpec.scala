package infra.mysql.tables

import scalikejdbc._
import scalikejdbc.scalatest.AutoRollback
import org.scalatest.fixture.FlatSpec
import org.scalatest._

import models.user.UnverifiedUserId

class UserAuthsTableSpec extends FlatSpec with Matchers with AutoRollback with TestDBSettings {

  override def fixture(implicit session: DBSession) {
    val sql = SQL("insert into user_auths(user_id, email, hashed_password) values (?, ?, ?)")
    sql.bind("4e7015a4-0000-0000-0000-000000000001", "user_01@example.com", "thisishashedpassword01").update.apply()
    sql.bind("4e7015a4-0000-0000-0000-000000000002", "user_02@example.com", "thisishashedpassword02").update.apply()
    sql.bind("4e7015a4-0000-0000-0000-000000000003", "user_03@example.com", "thisishashedpassword03").update.apply()
  }

  it should "select" in { implicit session =>
    val userId = UnverifiedUserId.fromString("4e7015a4-0000-0000-0000-000000000002")
    val recordOption = UserAuthsTable.findById(userId)
    val answer = Some(UserAuthRecord(
      user_id = "4e7015a4-0000-0000-0000-000000000002",
      email = "user_02@example.com",
      hashed_password = "thisishashedpassword02"
    ))

    recordOption shouldBe answer
  }

  it should "insert" in { implicit session =>
    val insertRecord = UserAuthRecord(
      user_id = "4e7015a4-0000-0000-0000-000000000004",
      email = "user_04@example.com",
      hashed_password = "thisishashedpassword04"
    )
    val parameters = UserAuthsTable.columnsAndValues(insertRecord)
    // UserAuthsTable.createWithAttributes(parameters: _*)
    UserAuthsTable.createWithAttributes(
      Symbol("user_id")         -> "4e7015a4-0000-0000-0000-000000000004",
      Symbol("email")          -> "user_04@example.com",
      Symbol("hashed_password") -> "thisishashedpassword04"
    )

    val userId = UnverifiedUserId.fromString("4e7015a4-0000-0000-0000-000000000004")
    val recordOption = UserAuthsTable.findById(userId)

    insertRecord shouldBe recordOption.get
  }

  it should "update" in { implicit session =>
    val userId = UnverifiedUserId.fromString("4e7015a4-0000-0000-0000-000000000003")
    UserAuthsTable.updateById(userId).withAttributes(
      Symbol("email")           -> "user_02_changed@example.com",
      Symbol("hashed_password") -> "newpassword02"
    )
    val updatedRecordOption = UserAuthsTable.findById(userId)

    val answer = Some(UserAuthRecord(
      user_id = "4e7015a4-0000-0000-0000-000000000003",
      email = "user_02_changed@example.com",
      hashed_password = "newpassword02"
    ))

    updatedRecordOption shouldBe answer
  }

}
