package infra.mysql.tables

import scalikejdbc._
import scalikejdbc.scalatest.AutoRollback
import org.scalatest.fixture.FlatSpec
import org.scalatest._

import models.user.UnverifiedUserToken

class UserTokensTableSpec extends FlatSpec with Matchers with AutoRollback with TestDBSettings {

  override def fixture(implicit session: DBSession) {
    val sql = SQL("insert into user_tokens(user_token, user_id) values (?, ?)")
    sql.bind("Bearer fsGrrFn~vmr-aIRcPL1LZ4hDGkvmWSQpdGTnxSsN/LoWjdp3rO2UiYCZf", "4e7015a4-0000-0000-0000-000000000001").update.apply()
    sql.bind("Bearer -PHakjP5ai5.06vaPQRrqMFAgNHm5WPNAtgCAv5_7ZXScyapdlBBZ9s2Z", "4e7015a4-0000-0000-0000-000000000002").update.apply()
    sql.bind("Bearer hR66-k6/18GIjz-6+Gj3DCj-5rR0Z6pWxy0PqgnODlHWziS6.qB/uHZh1", "4e7015a4-0000-0000-0000-000000000003").update.apply()
  }

  it should "select" in { implicit session =>
    val token = UnverifiedUserToken.fromString("Bearer -PHakjP5ai5.06vaPQRrqMFAgNHm5WPNAtgCAv5_7ZXScyapdlBBZ9s2Z")
    val recordOption = UserTokensTable.findById(token)
    val answer = Some(UserTokenRecord(
      user_token = "Bearer -PHakjP5ai5.06vaPQRrqMFAgNHm5WPNAtgCAv5_7ZXScyapdlBBZ9s2Z",
      user_id = "4e7015a4-0000-0000-0000-000000000002",
    ))

    recordOption shouldBe answer
  }

  it should "insert" in { implicit session =>
    val insertRecord = UserTokenRecord(
      user_token = "Bearer C7dXvISai2AtbLGbJ_UkhdUCXcaqBfDhAr-4~fu_1XR.So3sR~8hMKjoc",
      user_id = "4e7015a4-0000-0000-0000-000000000004"
    )
    val parameters = UserTokensTable.columnsAndValues(insertRecord)
    UserTokensTable.createWithAttributes(parameters: _*)

    val token = UnverifiedUserToken.fromString("Bearer C7dXvISai2AtbLGbJ_UkhdUCXcaqBfDhAr-4~fu_1XR.So3sR~8hMKjoc")
    val recordOption = UserTokensTable.findById(token)

    insertRecord shouldBe recordOption.get
  }

  it should "update" in { implicit session =>
    val token = UnverifiedUserToken.fromString("Bearer hR66-k6/18GIjz-6+Gj3DCj-5rR0Z6pWxy0PqgnODlHWziS6.qB/uHZh1")
    UserTokensTable.updateById(token).withAttributes(
      Symbol("user_id") -> "4e7015a4-0000-0000-1234-000000000003"
    )
    val updatedRecordOption = UserTokensTable.findById(token)

    val answer = Some(UserTokenRecord(
      user_token = "Bearer hR66-k6/18GIjz-6+Gj3DCj-5rR0Z6pWxy0PqgnODlHWziS6.qB/uHZh1",
      user_id = "4e7015a4-0000-0000-1234-000000000003"
    ))

    updatedRecordOption shouldBe answer
  }

  it should "delete" in { implicit session =>
    val token = UnverifiedUserToken.fromString("Bearer hR66-k6/18GIjz-6+Gj3DCj-5rR0Z6pWxy0PqgnODlHWziS6.qB/uHZh1")
    UserTokensTable.findById(token).isDefined shouldBe true

    UserTokensTable.deleteById(token)
    UserTokensTable.findById(token).isDefined shouldBe false
  }

}
