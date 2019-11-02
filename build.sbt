import Dependencies._

ThisBuild / scalaVersion     := "2.13.0"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.kazmiy"

scalacOptions ++= "-deprecation" :: "-feature" :: "-Xlint" :: Nil
scalacOptions in (Compile, console) ~= {_.filterNot(_ == "-Xlint")}

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := "serviver-chat-play",
    libraryDependencies += guice,
  )
  .dependsOn(
    models,
    application,
    infra
  )

lazy val models = (project in file("mdoels"))
  .settings(
    name := "serviver-chat-play-models",
  )

lazy val application = (project in file("application"))
  .enablePlugins(PlayScala)
  .settings(
    name := "serviver-chat-play-application",
    libraryDependencies += scalaTestPlusPlay % Test,
    libraryDependencies ++= circe,
  )
  .dependsOn(models)

lazy val infra = (project in file("infra"))
  .enablePlugins(PlayScala)
  .settings(
    name := "serviver-chat-play-infra",
    libraryDependencies ++= scalikejdbc,
    libraryDependencies += awsJavaSdkDynamoDB,
  )
  .dependsOn(
    models,
    application
  )
