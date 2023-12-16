package controllers.dto.getSave

import derevo.circe.{decoder, encoder}
import derevo.derive
import domain.Save
import sttp.tapir.Schema

@derive(encoder, decoder)
case class GetSaveResponse (foundSave: Option[Save])

object GetSaveResponse {
  implicit lazy val schema: Schema[GetSaveResponse] = Schema.derived
}