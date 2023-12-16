package controllers.dto.getSave

import controllers.dto.createSave.CreateSaveRequest
import derevo.circe.{decoder, encoder}
import derevo.derive
import sttp.tapir.Schema

import java.util.UUID

@derive(encoder, decoder)
final case class GetSaveRequest (userId: UUID, novelId: UUID, nodeId: UUID)

object GetSaveRequest {
  implicit lazy val schema: Schema[GetSaveRequest] = Schema.derived
}