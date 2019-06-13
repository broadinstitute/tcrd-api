import sbt.Keys.libraryDependencies

ThisBuild / version := "0.0.1"
ThisBuild / scalaVersion := "2.12.8"

lazy val web = (project in file("web"))
  .dependsOn(db)
  .enablePlugins(PlayJava)
  .settings(
    name := "tcrd-web",
    libraryDependencies ++=
      Seq(
        "org.webjars" % "swagger-ui" % "3.1.5",
        "javax.validation" % "validation-api" % "1.1.0.Final",
        guice
      )
  )

lazy val db = (project in file("db"))
  .settings(
    name := "tcrd-db"
  )
