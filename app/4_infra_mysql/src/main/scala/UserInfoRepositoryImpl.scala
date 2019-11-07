package infra.mysql

import entity.user.repository.UserInfoRepository
import entity.user._

import infra.mysql.tables.{UserInfoRecord, UserInfosTable}
import scalikejdbc._

import scala.util.Try

class UserInfoRepositoryImpl extends UserInfoRepository {

  import UserInfoRepository._

  def findAll(): List[(VerifiedUserId, UserInfo)] = {
    UserInfosTable.findAll().map(_.toEntity)
  }

  def findBy(userId: UnverifiedUserId): Option[(VerifiedUserId, UserInfo)] = {
    UserInfosTable.findById(userId).map(_.toEntity)
  }

  def create(userId: GeneratedUserId, userInfo: UserInfo): Either[CreateError, VerifiedUserId] = {
    import java.sql.SQLIntegrityConstraintViolationException

    Try {
      val record     = UserInfoRecord.fromEntity(userId, userInfo)
      val parameters = UserInfosTable.columnsAndValues(record)
      UserInfosTable.createWithAttributes(parameters: _*)
      VerifiedUserId(userId.value)
    }.toEither.left
      .map {
        case e: SQLIntegrityConstraintViolationException => DuplicateUserError(e)
        case e                                           => UnknownError(e)
      }
  }

  def update(userId: VerifiedUserId, userInfo: UserInfo): Unit = {
    val record     = UserInfoRecord.fromEntity(userId, userInfo)
    val parameters = UserInfosTable.updateAttributes(record)
    UserInfosTable.updateById(userId).withAttributes(parameters: _*)
  }

}
