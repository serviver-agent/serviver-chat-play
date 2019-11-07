package infra.mysql.tables

import scalikejdbc._
import scalikejdbc.scalatest.AutoRollback
import org.scalatest.fixture.FlatSpec
import org.scalatest._

import models.user.{UnverifiedUserId, GeneratedUserId}

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
    UserAuthsTable.createWithAttributes(parameters: _*)

    val userId = UnverifiedUserId.fromString("4e7015a4-0000-0000-0000-000000000004")
    val recordOption = UserAuthsTable.findById(userId)

    insertRecord shouldBe recordOption.get
  }

  it should "update" in { implicit session =>
    val userId = UnverifiedUserId.fromString("4e7015a4-0000-0000-0000-000000000003")
    UserAuthsTable.updateById(userId).withAttributes(
      Symbol("email")           -> "user_03_changed@example.com",
      Symbol("hashed_password") -> "newpassword03"
    )
    val updatedRecordOption = UserAuthsTable.findById(userId)

    val answer = Some(UserAuthRecord(
      user_id = "4e7015a4-0000-0000-0000-000000000003",
      email = "user_03_changed@example.com",
      hashed_password = "newpassword03"
    ))

    updatedRecordOption shouldBe answer
  }

  it should "delete" in { implicit session =>
    val userId = UnverifiedUserId.fromString("4e7015a4-0000-0000-0000-000000000003")
    UserAuthsTable.findById(userId).isDefined shouldBe true

    UserAuthsTable.deleteById(userId)
    UserAuthsTable.findById(userId).isDefined shouldBe false
  }

  it should "not register duplicate email addresses" in { implicit session =>
    import java.sql.SQLIntegrityConstraintViolationException

    val insertRecord1 = UserAuthRecord(
      user_id = GeneratedUserId.create().display,
      email = "user_05@example.com",
      hashed_password = "password"
    )
    val parameters1 = UserAuthsTable.columnsAndValues(insertRecord1)
    UserAuthsTable.createWithAttributes(parameters1: _*)

    val insertRecord2 = UserAuthRecord(
      user_id = GeneratedUserId.create().display,
      email = "user_05@example.com",
      hashed_password = "password"
    )
    val parameters2 = UserAuthsTable.columnsAndValues(insertRecord2)
    assertThrows[SQLIntegrityConstraintViolationException] {
      UserAuthsTable.createWithAttributes(parameters2: _*)
    }
  }

}
