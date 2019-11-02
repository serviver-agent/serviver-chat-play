package infra.dynamo.common

import javax.inject.{Singleton, Inject}
import configs.DynamoConfig
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.auth.{AWSStaticCredentialsProvider, BasicAWSCredentials}

trait DynamoDBFactory {
  def dynamoDB: AmazonDynamoDB
}

@Singleton
class DynamoDBFactoryImpl @Inject() (config: DynamoConfig) extends DynamoDBFactory {

  override def dynamoDB: AmazonDynamoDB = {
    AmazonDynamoDBClientBuilder
      .standard()
      .withEndpointConfiguration(new EndpointConfiguration(config.region, config.endPoint))
      .withCredentials(
        new AWSStaticCredentialsProvider(
          new BasicAWSCredentials(config.accessKey, config.secretKey)
        )
      )
      .build
  }

}
