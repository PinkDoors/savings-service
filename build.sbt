ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-effect" % "3.5.0",
  "org.tpolecat" %% "doobie-core" % "1.0.0-RC2",
  "org.tpolecat" %% "doobie-hikari" % "1.0.0-RC2", // HikariCP transactor.
  "org.tpolecat" %% "doobie-postgres" % "1.0.0-RC2", // Postgres driver 42.3.1 + type mappings.
  "tf.tofu" %% "tofu-logging" % "0.12.0.1",
  "tf.tofu" %% "tofu-logging-derivation" % "0.12.0.1",
  "tf.tofu" %% "tofu-logging-layout" % "0.12.0.1",
  "tf.tofu" %% "tofu-logging-logstash-logback" % "0.12.0.1",
  "tf.tofu" %% "tofu-logging-structured" % "0.12.0.1",
  "tf.tofu" %% "tofu-core-ce3" % "0.12.0.1",
  "tf.tofu" %% "tofu-doobie-logging-ce3" % "0.12.0.1",
  "tf.tofu" %% "derevo-pureconfig" % "0.13.0",
  "com.softwaremill.sttp.client3" %% "core" % "3.8.15",
  "tf.tofu" %% "derevo-circe" % "0.13.0",
  "io.estatico" %% "newtype" % "0.4.4",
  "com.github.pureconfig" %% "pureconfig" % "0.17.4",
  "org.http4s" %% "http4s-ember-server" % "0.23.19",
  "io.github.kirill5k" %% "mongo4cats-core"  % "0.6.10",
  "io.github.kirill5k" %% "mongo4cats-circe" % "0.6.10",
  "com.github.pureconfig" %% "pureconfig" % "0.17.4",
  "com.softwaremill.sttp.tapir" %% "tapir-derevo" % "1.9.2",
  "com.tethys-json" %% "tethys" % "0.26.0",
  "com.tethys-json" %% "tethys-derivation" % "0.26.0",
  "com.softwaremill.sttp.client3" %% "core" % "3.8.15",
  "com.softwaremill.sttp.client3" %% "async-http-client-backend-cats" % "3.8.15",
  "com.softwaremill.sttp.tapir" %% "tapir-sttp-client" % "1.9.2",
  "com.softwaremill.sttp.tapir" %% "tapir-cats-effect" % "1.9.2",
  "com.softwaremill.sttp.tapir" %% "tapir-prometheus-metrics" % "1.9.2",
  "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % "1.9.2",
  "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % "1.9.2",
  "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui" % "1.9.2",
  "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs" % "1.9.2",
  "com.softwaremill.sttp.tapir" %% "tapir-sttp-stub-server" % "1.9.2" % Test,
  "com.softwaremill.sttp.tapir" %% "tapir-json-tethys" % "1.9.2",
  "com.softwaremill.sttp.tapir" %% "tapir-core" % "1.9.2",
  "com.softwaremill.sttp.tapir" %% "tapir-http4s-server" % "1.9.2",
  "com.softwaremill.sttp.client3" %% "circe" % "3.9.1" % Test
)

scalacOptions ++= Seq("-Ymacro-annotations")

dependencyOverrides += "io.circe" %% "circe-core" % "0.14.3"

lazy val root = (project in file("."))
  .settings(
    name := "Project"
  )
