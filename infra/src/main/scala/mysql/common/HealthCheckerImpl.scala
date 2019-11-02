package infra.mysql.common

import utils.HealthChecker

import scalikejdbc._
import scalikejdbc.config._

class HealthCheckerImpl extends HealthChecker {

  // DBs.setupAll()

  override def check(): Boolean = {
    DB localTx { implicit session =>
      sql"select 1".execute.apply()
    }
  }

}
