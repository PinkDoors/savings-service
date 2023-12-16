package domain

import cats.syntax.option._
import derevo.circe.{decoder, encoder}
import derevo.derive
import io.circe.{Decoder, Encoder, HCursor, Json}
import sttp.tapir.Schema

import java.util.UUID

object errors {
  @derive(encoder, decoder)
  sealed abstract class AppError(
      val message: String,
      val cause: Option[Throwable] = None
  )

  @derive(encoder, decoder)
  case class SaveAlreadyExists()
    extends AppError(
      "Save for given userId and novelId already exists."
    )

  @derive(encoder, decoder)
  case class SaveNotFound()
    extends AppError(
      s"Save for given userId and novelId not found."
    )
}
