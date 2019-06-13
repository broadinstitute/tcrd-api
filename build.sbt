ThisBuild / version      := "0.0.1"
ThisBuild / scalaVersion := "2.12.8"

lazy val web = (project in file("web"))

lazy val db = (project in file("db"))
