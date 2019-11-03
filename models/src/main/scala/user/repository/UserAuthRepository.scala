package models.user.repository

import models.user._

trait UserAuthRepository {
  def save(): Unit
}
