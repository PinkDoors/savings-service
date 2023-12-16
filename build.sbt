import Dependencies.{http4sEmber, mongo4cats, pureconfig, tapir, tethys, tofu}

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

libraryDependencies ++= Seq(
  pureconfig,
  http4sEmber
) ++ tapir.modules ++ tethys.modules ++ tofu.modules ++ tofu.loggingModules ++ tofu.pureconfigModules ++ mongo4cats.modules

scalacOptions ++= Seq("-Ymacro-annotations")

dependencyOverrides += "io.circe" %% "circe-core" % "0.14.3"

lazy val root = (project in file("."))
  .settings(
    name := "Project"
  )
