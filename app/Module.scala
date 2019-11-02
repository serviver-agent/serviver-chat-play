import com.google.inject.AbstractModule

import models.UserRepository
import infra.mysql.UserRepositoryImpl

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[UserRepository]).to(classOf[UserRepositoryImpl])
  }
}
