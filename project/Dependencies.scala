import Dependencies.tapir.tapirVersion
import sbt.*

object Dependencies {
  val pureconfig = "com.github.pureconfig" %% "pureconfig" % "0.17.4"
  val http4sEmber = "org.http4s" %% "http4s-ember-server" % "0.23.19"

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

  object tethys {
    val tethysVersion = "0.26.0"
    val modules: List[ModuleID] = List(
      "com.tethys-json" %% "tethys" % tethysVersion,
      "com.tethys-json" %% "tethys-derivation" % tethysVersion
    )
  }

  object tofu {
    val tofuVersion = "0.12.0.1"
    val modules: List[ModuleID] = List(
      "tf.tofu" %% "tofu-core-ce3" % tofuVersion,
      "tf.tofu" %% "tofu-kernel" % tofuVersion,
    )

    val derevoPureConfigVersion = "0.13.0"
    val pureconfigModules: List[ModuleID] = List(
      "tf.tofu" %% "derevo-circe" % derevoPureConfigVersion,
      "tf.tofu" %% "derevo-pureconfig" % derevoPureConfigVersion
    )

    val loggingModules: List[ModuleID] = List(
      "tf.tofu" %% "tofu-logging" % tofuVersion,
      "tf.tofu" %% "tofu-logging-derivation" % tofuVersion,
      "tf.tofu" %% "tofu-logging-layout" % tofuVersion,
      "tf.tofu" %% "tofu-logging-logstash-logback" % tofuVersion,
      "tf.tofu" %% "tofu-logging-structured" % tofuVersion
    )
  }

  object mongo4cats {
    val mongo4catsVersion = "0.6.10"
    val modules: List[ModuleID] = List(
      "io.github.kirill5k" %% "mongo4cats-core" % mongo4catsVersion,
      "io.github.kirill5k" %% "mongo4cats-circe" % mongo4catsVersion,
    )
  }
}
