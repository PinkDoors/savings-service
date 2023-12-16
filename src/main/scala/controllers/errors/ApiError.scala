package controllers.errors

import derevo.circe.{decoder, encoder}
import derevo.derive
import sttp.tapir.Schema

sealed trait ApiError

@derive(encoder, decoder)
final case class ServerError(
    message: String
) extends ApiError

@derive(encoder, decoder)
final case class ConflictClientError(
    message: String
) extends ApiError

@derive(encoder, decoder)
final case class NotFoundClientError(
    message: String
) extends ApiError

//final case class UnauthorizedApiError(
//                                       message: String
//                                     ) extends ApiError

object ApiError {
  implicit val conflictBusinessApiErrorSchema: Schema[ConflictClientError] =
    Schema.derived
  implicit val notFoundBusinessApiErrorSchema: Schema[NotFoundClientError] =
    Schema.derived
  implicit val serverApiErrorSchema: Schema[ServerError] = Schema.derived
}
