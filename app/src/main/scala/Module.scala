import com.google.inject.AbstractModule

import application.utils.HealthChecker
import infra.mysql.common.HealthCheckerImpl
// import models.UserRepository // TODO
// import infra.mysql.UserRepositoryImpl // TODO
import models.channel.repository.ChannelRepository
import infra.mysql.ChannelRepositoryImpl
import models.user.repository.UserTokenRepository
import infra.dynamo.UserTokenRepositoryImpl
import infra.dynamo.common.{DynamoDBFactory, DynamoDBFactoryImpl}
import application.configs.{DynamoConfig, DynamoConfigImpl}

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[HealthChecker]).to(classOf[HealthCheckerImpl])
    // bind(classOf[UserRepository]).to(classOf[UserRepositoryImpl]) // TODO
    bind(classOf[ChannelRepository]).to(classOf[ChannelRepositoryImpl])
    bind(classOf[UserTokenRepository]).to(classOf[UserTokenRepositoryImpl])
    bind(classOf[DynamoDBFactory]).to(classOf[DynamoDBFactoryImpl])
    bind(classOf[DynamoConfig]).to(classOf[DynamoConfigImpl])
  }
}
