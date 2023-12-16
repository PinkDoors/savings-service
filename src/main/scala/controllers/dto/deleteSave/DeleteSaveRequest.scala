package controllers.dto.deleteSave

import derevo.circe.{decoder, encoder}
import derevo.derive
import sttp.tapir.Schema

import java.util.UUID

@derive(encoder, decoder)
case class DeleteSaveRequest (userId: UUID, novelId: UUID)

object DeleteSaveRequest {
  implicit lazy val schema: Schema[DeleteSaveRequest] = Schema.derived
}
