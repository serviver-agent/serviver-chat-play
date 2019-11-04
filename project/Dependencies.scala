import sbt._

object Dependencies {
  lazy val scalatest = "org.scalatest" %% "scalatest" % "3.0.8"
  lazy val scalaTestPlusPlay = "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3"
  lazy val playframework = "com.typesafe.play" %% "play" % "2.7.3"
  lazy val mysql = "mysql" % "mysql-connector-java" % "8.0.17"
  lazy val scalikejdbc = Seq(
    "org.scalikejdbc" %% "scalikejdbc" % "3.3.5",
    "org.scalikejdbc" %% "scalikejdbc-config" % "3.3.5",
    "org.scalikejdbc" %% "scalikejdbc-play-dbapi-adapter" % "2.7.1-scalikejdbc-3.3"
  )
  lazy val skinnyOrm = "org.skinny-framework" %% "skinny-orm" % "3.0.3"
  lazy val awsJavaSdkDynamoDB = "com.amazonaws" % "aws-java-sdk" % "1.11.664"
  lazy val circe = Seq(
    "io.circe" %% "circe-core" % "0.12.0-M3",
    "io.circe" %% "circe-generic" % "0.12.0-M3",
    "io.circe" %% "circe-parser" % "0.12.0-M3"
  )
  lazy val javaxInject = "javax.inject" % "javax.inject" % "1"
  lazy val springSecurityWeb = "org.springframework.security" % "spring-security-web" % "5.2.0.RELEASE"
}
