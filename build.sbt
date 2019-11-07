import Dependencies._

ThisBuild / scalaVersion     := "2.13.0"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.kazmiy"

scalacOptions ++= "-deprecation" :: "-feature" :: "-Xlint" :: Nil
scalacOptions in (Compile, console) ~= {_.filterNot(_ == "-Xlint")}

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  // .disablePlugins(PlayLayoutPlugin)
  .settings(
    name := "serviver-chat-play",
    // javaOptions in Runtime += "-Dconfig.resource=src/resources/application.conf",
    // javaOptions in Runtime += "-Dapplication.router=src/resources/routes"
    // confDirectory := "src/resources" 
  )
  .dependsOn(
    entity,
    usecase,
    adapter,
    mysql,
    dynamodb,
    injector,
    boot
  )
  .aggregate(
    entity,
    usecase,
    adapter,
    mysql,
    dynamodb,
    injector,
    boot
  )

lazy val entity = (project in file("app/1_entity"))
  .settings(
    name := "serviver-chat-play-models",
    libraryDependencies += scalatest % Test,
    libraryDependencies += javaxInject,
    libraryDependencies += springSecurityWeb,
  )

lazy val usecase = (project in file("app/2_usecase"))
  .dependsOn(entity)

lazy val adapter = (project in file("app/3_adapter"))
  .settings(
    name := "serviver-chat-play-application",
    libraryDependencies += playframework,
    libraryDependencies += scalaTestPlusPlay % Test,
    libraryDependencies ++= circe,
  )
  .dependsOn(
    entity,
    usecase
  )

lazy val mysql = (project in file("app/4_infra_mysql"))
  .settings(
    name := "serviver-chat-play-infra",
    libraryDependencies += scalatest % Test,
    libraryDependencies += mysqlConnector,
    libraryDependencies ++= scalikejdbc,
    libraryDependencies += skinnyOrm,
  )
  .dependsOn(
    entity,
    usecase,
    adapter
  )

lazy val dynamodb = (project in file("app/4_infra_dynamodb"))
  .settings(
    name := "serviver-chat-play-infra",
    libraryDependencies += scalatest % Test,
    libraryDependencies += awsJavaSdkDynamoDB,
  )
  .dependsOn(
    entity,
    usecase,
    adapter
  )

lazy val injector = (project in file("app/5_injector"))
  .settings(
    name := "serviver-chat-play-injector",
    libraryDependencies += guice,
  )
  .dependsOn(
    entity,
    usecase,
    adapter,
    mysql,
    dynamodb
  )

lazy val boot = (project in file("app/6_boot"))
  .settings(
    name := "serviver-chat-play-boot",
  )
  .dependsOn(
    entity,
    usecase,
    adapter,
    mysql,
    dynamodb,
    injector
  )
