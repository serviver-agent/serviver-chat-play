package models.user

case class UserInfo(
    userName: UserInfo.UserName,
    imageUrl: UserInfo.ImageUrl
)

object UserInfo {

  def create(userNameStr: String, imageUrlStr: String) = UserInfo(
    UserName(userNameStr),
    ImageUrl(imageUrlStr)
  )

  case class UserName(value: String) {
    def display: String = value
  }
  case class ImageUrl(value: String) {
    def display: String = value
  }

}
