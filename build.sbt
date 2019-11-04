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
  .aggregate(
    models,
    application,
    infra
  )

lazy val models = (project in file("models"))
  .settings(
    name := "serviver-chat-play-models",
    libraryDependencies += scalatest % Test,
    libraryDependencies += javaxInject,
    libraryDependencies += springSecurityWeb,
  )

lazy val application = (project in file("application"))
  .settings(
    name := "serviver-chat-play-application",
    libraryDependencies += playframework,
    libraryDependencies += scalaTestPlusPlay % Test,
    libraryDependencies ++= circe,
  )
  .dependsOn(models)

lazy val infra = (project in file("infra"))
  .settings(
    name := "serviver-chat-play-infra",
    libraryDependencies += scalatest % Test,
    libraryDependencies += mysql,
    libraryDependencies ++= scalikejdbc,
    libraryDependencies += skinnyOrm,
    libraryDependencies += awsJavaSdkDynamoDB,
  )
  .dependsOn(
    models,
    application
  )
