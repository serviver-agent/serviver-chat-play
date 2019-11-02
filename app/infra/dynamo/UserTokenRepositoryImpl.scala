package infra.dynamo

import javax.inject.{Singleton, Inject}
import infra.dynamo.common.DynamoDBFactory
import models.{UserId, UserToken, UserTokenRepository}

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB

@Singleton
class UserTokenRepositoryImpl @Inject() (
    dynamoDBFactory: DynamoDBFactory
) extends UserTokenRepository {

  val dynamoDB: AmazonDynamoDB = dynamoDBFactory.dynamoDB

  override def findBy(token: UserToken): Option[UserId] = ???
  override def create(userId: UserId): UserToken        = ???
  override def delete(token: UserToken): Unit           = ???

}
