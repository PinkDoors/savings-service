package domain

import derevo.circe.{decoder, encoder}
import derevo.derive
import sttp.tapir.Schema

import java.util.UUID

@derive(encoder, decoder)
final case class Save private (userId: UUID, novelId: UUID, nodeId: UUID)

object Save {
  implicit lazy val schemaSave: Schema[Save] = Schema.derived.description("Test description")
//  implicit val throwableEncoder: Encoder[UUID] =
//    Encoder.encodeString.contramap(_.toString)
//  implicit val throwableDecoder: Decoder[UUID] =
//    Decoder.decodeString.map(UUID.fromString)
//  implicit val saveEncoder: Encoder[UUID] = deriveEncoder[UUID]
//  implicit val saveDecoder: Decoder[UUID] = deriveDecoder[UUID]
}
