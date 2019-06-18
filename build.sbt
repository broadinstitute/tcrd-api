import sbt.Keys.libraryDependencies

ThisBuild / version := "0.0.1"
ThisBuild / scalaVersion := "2.12.8"

val ScalatraVersion = "2.6.2"

lazy val model = (project in file("model"))
  .settings(
    name := "tcrd-model"
  )

lazy val db = (project in file("db"))
  .dependsOn(model)
  .settings(
    name := "tcrd-db",
    libraryDependencies ++= Seq(
      "com.github.pathikrit" %% "better-files" % "3.8.0",
      "org.scalatest" %% "scalatest" % "3.0.8" % "test",
      "org.scalikejdbc" %% "scalikejdbc"       % "3.3.2",
      "com.h2database"  %  "h2"                % "1.4.199",
      "ch.qos.logback"  %  "logback-classic"   % "1.2.3"
    )
  )

lazy val web = (project in file("web"))
  .dependsOn(model, db) 
  .enablePlugins(JettyPlugin)
  .settings(
    name := "tcrd-web",
    mainClass in assembly := Some("JettyMain"),
    libraryDependencies ++= Seq(
      "org.scalatra" %% "scalatra" % ScalatraVersion,
      "org.scalatra" %% "scalatra-swagger" % ScalatraVersion,
      "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % Test,
      "org.json4s" %% "json4s-jackson" % "3.5.0",
      "org.eclipse.jetty" % "jetty-server" % "9.4.8.v20171121",
      "org.eclipse.jetty" % "jetty-webapp" % "9.4.8.v20171121",
      "javax.servlet" % "javax.servlet-api" % "3.1.0",
      "ch.qos.logback" % "logback-classic" % "1.2.3" % Provided
    )
  )





