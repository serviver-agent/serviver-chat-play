package infra.dynamo

import javax.inject.{Singleton, Inject}
import infra.dynamo.common.DynamoDBFactory

import java.util.UUID

import models.user._
import models.user.repository.UserTokenRepository

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB

@Singleton
class UserTokenRepositoryImpl @Inject() (
    dynamoDBFactory: DynamoDBFactory
) extends UserTokenRepository {

  val dynamoDB: AmazonDynamoDB = dynamoDBFactory.dynamoDB

  import UserTokenRepositoryImpl._

  override def findBy(userToken: UnverifiedUserToken): Option[VerifiedUserId] = {
    import com.amazonaws.services.dynamodbv2.model.GetItemRequest
    import com.amazonaws.services.dynamodbv2.model.GetItemResult

    val key                   = UserTokensTable.attributeValueKey(userToken)
    val request               = new GetItemRequest(UserTokensTable.TableName, key)
    val result: GetItemResult = dynamoDB.getItem(request)

    if (result.getItem.size == 0) None
    else {
      val record = UserTokenRecord.fromAttributeValues(result.getItem)
      Some(VerifiedUserId(UUID.fromString(record.userId)))
    }
  }

  override def create(userId: VerifiedUserId): VerifiedUserToken = {
    import com.amazonaws.services.dynamodbv2.model.PutItemRequest

    val userToken       = GeneratedUserToken.createRandomToken()
    val userTokenRecord = UserTokenRecord.fromEntity(userToken, userId)

    val request = new PutItemRequest(UserTokensTable.TableName, userTokenRecord.toAttributeValues)
    dynamoDB.putItem(request)

    VerifiedUserToken(userToken.value)
  }

  override def delete(userToken: UserToken): Unit = {
    import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest

    val key = UserTokensTable.attributeValueKey(userToken)

    val request = new DeleteItemRequest(UserTokensTable.TableName, key)
    dynamoDB.deleteItem(request)
  }

  override def deleteBy(userId: VerifiedUserId): Unit = ??? // TODO

}

object UserTokenRepositoryImpl {

  import com.amazonaws.services.dynamodbv2.model.AttributeValue

  case class UserTokenRecord(token: String, userId: String) {
    def toAttributeValues: java.util.Map[String, AttributeValue] = {
      val map = new java.util.HashMap[String, AttributeValue]()
      map.put("userToken", new AttributeValue().withS(token))
      map.put("userId", new AttributeValue().withS(userId))
      map
    }
  }

  object UserTokenRecord {
    def fromEntity(userToken: UserToken, userId: UserId): UserTokenRecord = {
      UserTokenRecord(userToken.display, userId.display)
    }
    def fromAttributeValues(item: java.util.Map[String, AttributeValue]): UserTokenRecord = {
      val userToken = item.get("userToken").getS
      val userId    = item.get("userId").getS
      UserTokenRecord(userToken, userId)
    }
  }

  object UserTokensTable {
    val TableName    = "UserTokens"
    val PartitionKey = "userToken"

    def attributeValueKey(userToken: UserToken): java.util.Map[String, AttributeValue] = {
      val key = new java.util.HashMap[String, AttributeValue]()
      key.put(UserTokensTable.PartitionKey, new AttributeValue().withS(userToken.display))
      key
    }
  }

}
