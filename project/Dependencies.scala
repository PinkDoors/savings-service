import Dependencies.tapir.tapirVersion
import sbt.*

object Dependencies {
  object tapir {
    val tapirVersion = "1.9.0"
    val modules: List[ModuleID] = List(
      "com.softwaremill.sttp.tapir" %% "tapir-cats-effect" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-sttp-stub-server" % tapirVersion % Test,
      "com.softwaremill.sttp.tapir" %% "tapir-json-tethys" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-http4s-server" % tapirVersion,
      "com.softwaremill.sttp.client3" %% "circe" % "3.9.1" % Test
    )
  }
}
