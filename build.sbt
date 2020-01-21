import Dependencies._

ThisBuild / name := "com.tass.flight-search-engine"
ThisBuild / version := "0.1.0"
ThisBuild / scalaVersion := "2.13.1"

lazy val root = project in file(".")

lazy val common = project in file("common")

lazy val scrapper = (project in file("scrapper"))
  .settings(libraryDependencies ++= scrapperDependencies)
  .dependsOn(common)

lazy val backend = (project in file("backend"))
  .enablePlugins(PlayScala)
  .settings(libraryDependencies ++= backendDependencies :+ guice)
  .dependsOn(common)
