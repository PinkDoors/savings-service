package controllers.dto.createSave

import derevo.circe.{decoder, encoder}
import derevo.derive
import sttp.tapir.Schema

import java.util.UUID

@derive(encoder, decoder)
final case class CreateSaveRequest (userId: UUID, novelId: UUID, nodeId: UUID)

object CreateSaveRequest {
  implicit lazy val schema: Schema[CreateSaveRequest] = Schema.derived
}