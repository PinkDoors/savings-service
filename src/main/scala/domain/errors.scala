package domain

import derevo.circe.{decoder, encoder}
import derevo.derive

import java.util.UUID

object errors {
  @derive(encoder, decoder)
  sealed abstract class AppError(val message: String)

  @derive(encoder, decoder)
  case class SaveAlreadyExists(userId: UUID, novelId: UUID)
      extends AppError(
        s"Save for userId: $userId and novelId: $novelId already exists."
      )

  @derive(encoder, decoder)
  case class SaveNotFound(userId: UUID, novelId: UUID)
      extends AppError(
        s"Save for userId: $userId and novelId: $novelId was not found."
      )
}
