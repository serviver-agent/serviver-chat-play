import com.google.inject.AbstractModule

import models.UserRepository
import infra.mysql.UserRepositoryImpl
import models.ChannelRepository
import infra.mysql.ChannelRepositoryImpl
import models.UserTokenRepository
import infra.dynamo.UserTokenRepositoryImpl
import infra.dynamo.common.{DynamoDBFactory, DynamoDBFactoryImpl}
import configs.{DynamoConfig, DynamoConfigImpl}

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[UserRepository]).to(classOf[UserRepositoryImpl])
    bind(classOf[ChannelRepository]).to(classOf[ChannelRepositoryImpl])
    bind(classOf[UserTokenRepository]).to(classOf[UserTokenRepositoryImpl])
    bind(classOf[DynamoDBFactory]).to(classOf[DynamoDBFactoryImpl])
    bind(classOf[DynamoConfig]).to(classOf[DynamoConfigImpl])
  }
}
