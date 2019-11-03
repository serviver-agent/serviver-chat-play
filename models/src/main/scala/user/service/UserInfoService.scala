package models.user.service

import models.user._

trait UserInfoService {

  def findBy(userId: UserId): UserId with UserInfo

}
