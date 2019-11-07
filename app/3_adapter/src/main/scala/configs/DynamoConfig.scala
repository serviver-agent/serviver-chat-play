package adapter.configs

import javax.inject.{Singleton, Inject}
import play.api.Configuration

trait DynamoConfig {
  def region: String
  def endPoint: String
  def accessKey: String
  def secretKey: String
}

@Singleton
class DynamoConfigImpl @Inject() (config: Configuration) extends DynamoConfig {

  lazy val region    = config.get[String]("dynamo.region")
  lazy val endPoint  = config.get[String]("dynamo.endPoint")
  lazy val accessKey = config.get[String]("dynamo.accessKey")
  lazy val secretKey = config.get[String]("dynamo.secretKey")

}
