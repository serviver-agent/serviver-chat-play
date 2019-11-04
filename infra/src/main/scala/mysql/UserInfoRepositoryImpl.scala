package infra.mysql

import models.user.repository.UserInfoRepository
import models.user._

import infra.mysql.tables.{UserInfoRecord, UserInfosTable}
import scalikejdbc._

class UserInfoRepositoryImpl extends UserInfoRepository {

  def findAll(): List[(VerifiedUserId, UserInfo)] = {
    UserInfosTable.findAll().map(_.toEntity)
  }

  def findBy(userId: UnverifiedUserId): Option[(VerifiedUserId, UserInfo)] = {
    UserInfosTable.findById(userId).map(_.toEntity)
  }

  def create(userId: GeneratedUserId, userInfo: UserInfo): Either[Exception, Unit] = {
    val record     = UserInfoRecord.fromEntity(userId, userInfo)
    val parameters = UserInfosTable.columnsAndValues(record)
    UserInfosTable.createWithAttributes(parameters: _*)
    Right()
  }

  def update(userId: VerifiedUserId, userInfo: UserInfo): Unit = {
    val record     = UserInfoRecord.fromEntity(userId, userInfo)
    val parameters = UserInfosTable.updateAttributes(record)
    UserInfosTable.updateById(userId).withAttributes(parameters: _*)
  }

}
