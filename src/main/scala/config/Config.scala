package config

import cats.effect.IO
import derevo.derive
import derevo.pureconfig.pureconfigReader
import pureconfig._

@derive(pureconfigReader)
final case class AppConfig(db: DbConfig, server: ServerConfig)
object AppConfig {
  def load: IO[AppConfig] =
    IO.delay(ConfigSource.default.loadOrThrow[AppConfig])
}

@derive(pureconfigReader)
final case class ServerConfig(host: String, port: Int)

@derive(pureconfigReader)
final case class DbConfig(
    uri: String,
    dbName: String,
    dbSaveCollection: String
)

object DbConfig {
  def load: IO[DbConfig] =
    IO.delay(ConfigSource.default.loadOrThrow[DbConfig])
}
