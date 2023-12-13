package controllers

import domain.Save
import domain.errors._
import sttp.tapir._
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe.jsonBody

import java.util.UUID

object endpoints {
  val createSaveEndpoint: PublicEndpoint[(UUID, UUID, UUID), AppError, Unit, Any] =
    endpoint.post
      .in("saves")
      .in(path[UUID]("userId"))
      .in(path[UUID]("novelId"))
      .in(path[UUID]("nodeId"))
      .errorOut(jsonBody[AppError])

  val getSaveEndpoint: PublicEndpoint[(UUID, UUID), AppError, Save, Any] =
    endpoint.get
      .in("saves")
      .in(path[UUID]("userId"))
      .in(path[UUID]("novelId"))
      .errorOut(jsonBody[AppError])
      .out(jsonBody[Save])

  val deleteSaveEndpoint: PublicEndpoint[(UUID, UUID), AppError, Unit, Any] =
    endpoint.delete
      .in("saves")
      .in(path[UUID]("userId"))
      .in(path[UUID]("novelId"))
      .errorOut(jsonBody[AppError])
      .out(jsonBody[Unit])

  val allEndpoints: List[PublicEndpoint[_, _, _, _]] =
    List(
      createSaveEndpoint,
      getSaveEndpoint,
      deleteSaveEndpoint
    )
}
