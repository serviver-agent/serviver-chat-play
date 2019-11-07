import com.google.inject.AbstractModule

import application.utils.HealthChecker
import infra.mysql.common.HealthCheckerImpl

import models.user.repository.{UserAuthRepository, UserInfoRepository, UserTokenRepository}
import infra.mysql.{UserAuthRepositoryImpl, UserInfoRepositoryImpl, UserTokenRepositoryImpl}

import models.channel.repository.ChannelRepository
import infra.mysql.ChannelRepositoryImpl

// import infra.dynamo.UserTokenRepositoryImpl
// import infra.dynamo.common.{DynamoDBFactory, DynamoDBFactoryImpl}
// import application.configs.{DynamoConfig, DynamoConfigImpl}

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[HealthChecker]).to(classOf[HealthCheckerImpl])
    bind(classOf[UserAuthRepository]).to(classOf[UserAuthRepositoryImpl])
    bind(classOf[UserInfoRepository]).to(classOf[UserInfoRepositoryImpl])
    bind(classOf[UserTokenRepository]).to(classOf[UserTokenRepositoryImpl])
    bind(classOf[ChannelRepository]).to(classOf[ChannelRepositoryImpl])
  }
}
