package infra.mysql.tables

case class UserAuthRecord(
    userId: String,
    email: String,
    hashedPassword: String
)
