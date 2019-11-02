import com.google.inject.AbstractModule

import models.UserRepository
import infra.mysql.UserRepositoryImpl
import models.ChannelRepository
import infra.mysql.ChannelRepositoryImpl

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[UserRepository]).to(classOf[UserRepositoryImpl])
    bind(classOf[ChannelRepository]).to(classOf[ChannelRepositoryImpl])
  }
}
