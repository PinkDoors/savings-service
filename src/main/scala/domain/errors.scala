package domain

import cats.syntax.option._
import derevo.circe.{decoder, encoder}
import derevo.derive
import io.circe.Decoder.Result
import io.circe.{Decoder, Encoder, HCursor, Json}
import sttp.tapir.Schema
import sttp.tapir.derevo.schema

import java.util.UUID

object errors {
  @derive(encoder, decoder)
  sealed abstract class AppError(
                                  val message: String,
                                  val cause: Option[Throwable] = None
                                )

  @derive(encoder, decoder)
  case class SaveAlreadyExists()
    extends AppError("Save for giver userId and novelId already exists.")

  @derive(encoder, decoder)
  case class SaveNotFound(userId: UUID, novelId: UUID)
    extends AppError(s"Save for userId ${userId} and novelId ${novelId} not found")

  @derive(encoder, decoder)
  case class InternalError(
                            cause0: Throwable
                          ) extends AppError("Internal error", cause0.some)
}
