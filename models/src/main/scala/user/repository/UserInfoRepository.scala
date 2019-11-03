package models.user.repository

import models.user._

trait UserInfoRepository {
  def save(): Unit
}
