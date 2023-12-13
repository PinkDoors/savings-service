package config

import cats.effect.IO
import pureconfig._
import pureconfig.generic.semiauto.deriveReader

final case class AppConfig(db: DbConfig, server: ServerConfig)
object AppConfig {
  implicit val reader: ConfigReader[AppConfig] = deriveReader

  def load: IO[AppConfig] =
    IO.delay(ConfigSource.default.loadOrThrow[AppConfig])
}

final case class ServerConfig(host: String, port: Int)
object ServerConfig {
  implicit val reader: ConfigReader[ServerConfig] = deriveReader
}

final case class DbConfig(
    host: String,
    port: Int,
    user: String,
    password: String,
    dbName: String,
    dbSaveCollection: String
)

object DbConfig {
  implicit val reader: ConfigReader[DbConfig] = deriveReader

  def load: IO[DbConfig] =
    IO.delay(ConfigSource.default.loadOrThrow[DbConfig])
}
