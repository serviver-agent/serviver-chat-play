package infra.mysql.tables

import scalikejdbc._
import scalikejdbc.config.DBsWithEnv

trait TestDBSettings {

  ConnectionPool.singleton("jdbc:mysql://127.0.0.1:3306/serviver-chat", "root", "")

}
