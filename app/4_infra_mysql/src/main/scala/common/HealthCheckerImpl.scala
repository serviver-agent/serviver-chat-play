package infra.mysql.common

import adapter.utils.HealthChecker

import scalikejdbc._
import scalikejdbc.config._

class HealthCheckerImpl extends HealthChecker {

  override def check(): Boolean = {
    DB localTx { implicit session =>
      sql"select 1".execute.apply()
    }
  }

}
