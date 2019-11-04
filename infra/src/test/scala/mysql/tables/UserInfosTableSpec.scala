package infra.mysql.tables

import scalikejdbc._
import scalikejdbc.scalatest.AutoRollback
import org.scalatest.fixture.FlatSpec
import org.scalatest._

import models.user.UnverifiedUserId

class UserInfosTableSpec extends FlatSpec with Matchers with AutoRollback with TestDBSettings {

  override def fixture(implicit session: DBSession) {
    val sql = SQL("insert into user_infos(user_id, user_name, image_url) values (?, ?, ?)")
    sql.bind("4e7015a4-0000-0000-0000-000000000001", "user_01", "http://www.example.com/images/01.jpg").update.apply()
    sql.bind("4e7015a4-0000-0000-0000-000000000002", "user_02", "http://www.example.com/images/02.jpg").update.apply()
    sql.bind("4e7015a4-0000-0000-0000-000000000003", "user_03", "http://www.example.com/images/03.jpg").update.apply()
  }

  it should "select" in { implicit session =>
    val userId = UnverifiedUserId.fromString("4e7015a4-0000-0000-0000-000000000002")
    val recordOption = UserInfosTable.findById(userId)
    val answer = Some(UserInfoRecord(
      user_id = "4e7015a4-0000-0000-0000-000000000002",
      user_name = "user_02",
      image_url = "http://www.example.com/images/02.jpg"
    ))

    recordOption shouldBe answer
  }

  it should "insert" in { implicit session =>
    val insertRecord = UserInfoRecord(
      user_id = "4e7015a4-0000-0000-0000-000000000004",
      user_name = "user_04",
      image_url = "http://www.example.com/images/04.jpg"
    )
    val parameters = UserInfosTable.columnsAndValues(insertRecord)
    UserInfosTable.createWithAttributes(parameters: _*)

    val userId = UnverifiedUserId.fromString("4e7015a4-0000-0000-0000-000000000004")
    val recordOption = UserInfosTable.findById(userId)

    insertRecord shouldBe recordOption.get
  }

  it should "update" in { implicit session =>
    val userId = UnverifiedUserId.fromString("4e7015a4-0000-0000-0000-000000000003")
    UserInfosTable.updateById(userId).withAttributes(
      Symbol("user_name")           -> "user_03_changed",
      Symbol("image_url") -> "http://www.example.com/images/03_2.jpg"
    )
    val updatedRecordOption = UserInfosTable.findById(userId)

    val answer = Some(UserInfoRecord(
      user_id = "4e7015a4-0000-0000-0000-000000000003",
      user_name = "user_03_changed",
      image_url = "http://www.example.com/images/03_2.jpg"
    ))

    updatedRecordOption shouldBe answer
  }

  it should "delete" in { implicit session =>
    val userId = UnverifiedUserId.fromString("4e7015a4-0000-0000-0000-000000000003")
    UserInfosTable.findById(userId).isDefined shouldBe true

    UserInfosTable.deleteById(userId)
    UserInfosTable.findById(userId).isDefined shouldBe false
  }

}
