package controllers

import controllers.dto.createSave.CreateSaveRequest
import controllers.dto.deleteSave.DeleteSaveRequest
import controllers.dto.getSave.GetSaveResponse
import controllers.dto.updateSave.UpdateSaveRequest
import controllers.errors.{
  ApiError,
  ConflictClientError,
  NotFoundClientError,
  ServerError
}
import sttp.model.StatusCode
import sttp.tapir._
import sttp.tapir.json.circe.jsonBody

import java.util.UUID

object endpoints {
  private val apiErrorEndpoint: PublicEndpoint[Unit, ApiError, Unit, Any] =
    endpoint
      .errorOut(
        oneOf[ApiError](
          oneOfVariant(
            StatusCode.InternalServerError,
            jsonBody[ServerError]
          ),
          oneOfVariant(
            StatusCode.Conflict,
            jsonBody[ConflictClientError]
          ),
          oneOfVariant(
            StatusCode.NotFound,
            jsonBody[NotFoundClientError]
          )
        )
      )

  val createSaveEndpoint
      : PublicEndpoint[CreateSaveRequest, ApiError, Unit, Any] =
    apiErrorEndpoint.post
      .in("save" / "create")
      .in(jsonBody[CreateSaveRequest])

  val getSaveEndpoint
      : PublicEndpoint[(UUID, UUID), ApiError, GetSaveResponse, Any] =
    apiErrorEndpoint.get
      .in("save" / "get")
      .in(query[UUID]("userId"))
      .in(query[UUID]("novelId"))
      .out(jsonBody[GetSaveResponse])

  val updateSaveEndpoint
      : PublicEndpoint[UpdateSaveRequest, ApiError, Unit, Any] =
    apiErrorEndpoint.put
      .in("save" / "update")
      .in(jsonBody[UpdateSaveRequest])

  val deleteSaveEndpoint
      : PublicEndpoint[DeleteSaveRequest, ApiError, Unit, Any] =
    apiErrorEndpoint.delete
      .in("save" / "delete")
      .in(jsonBody[DeleteSaveRequest])
}
