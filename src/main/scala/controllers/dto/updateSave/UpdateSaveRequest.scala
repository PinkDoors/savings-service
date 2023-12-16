package controllers.dto.updateSave

import derevo.circe.{decoder, encoder}
import derevo.derive
import sttp.tapir.Schema

import java.util.UUID

@derive(encoder, decoder)
case class UpdateSaveRequest (userId: UUID, novelId: UUID, newNodeId: UUID)

object UpdateSaveRequest {
  implicit lazy val schema: Schema[UpdateSaveRequest] = Schema.derived
}