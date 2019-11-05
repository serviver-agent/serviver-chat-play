
import com.softwaremill.macwire._

import application.utils.HealthChecker
import infra.mysql.common.HealthCheckerImpl

import models.user.repository.{UserAuthRepository, UserInfoRepository, UserTokenRepository}
import infra.mysql.{UserAuthRepositoryImpl, UserInfoRepositoryImpl, UserTokenRepositoryImpl}

import models.channel.repository.ChannelRepository
import infra.mysql.ChannelRepositoryImpl

// import infra.dynamo.UserTokenRepositoryImpl
// import infra.dynamo.common.{DynamoDBFactory, DynamoDBFactoryImpl}
// import application.configs.{DynamoConfig, DynamoConfigImpl}

import play.api.i18n.Langs
import play.api.mvc.ControllerComponents

trait MyApplicationModule {

  lazy val healthCheckerImpl = new HealthCheckerImpl
  lazy val userAuthRepositoryImpl = new UserAuthRepositoryImpl
  lazy val userInfoRepositoryImpl = new UserInfoRepositoryImpl
  lazy val userTokenRepositoryImpl = new UserTokenRepositoryImpl
  lazy val channelRepositoryImpl = new ChannelRepositoryImpl

  lazy val healthChecker = wire[HealthChecker]
  lazy val userAuthRepository = wire[UserAuthRepository]
  lazy val userInfoRepository = wire[UserInfoRepository]
  lazy val userTokenRepository = wire[UserTokenRepository]
  lazy val channelRepository = wire[ChannelRepository]

  def langs: Langs

  def controllerComponents: ControllerComponents

}
