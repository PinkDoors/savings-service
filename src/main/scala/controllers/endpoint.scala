package controllers

import sttp.tapir._
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe._

object endpoint {
  val baseEndpoint: Endpoint[Unit, String, Unit, Any] = endpoint.in("api" / "v1")

  val createSaveEndpoint: Endpoint[(UUID, UUID, UUID), String, Unit, Any] =
    baseEndpoint.post
      .in("saves")
      .in(path[UUID]("userId"))
      .in(path[UUID]("novelId"))
      .in(path[UUID]("node"))
      .out(jsonBody[Unit])
      .errorOut(stringBody)

  val getSaveEndpoint: Endpoint[(UUID, UUID), String, Option[Save], Any] =
    baseEndpoint.get
      .in("saves")
      .in(path[UUID]("userId"))
      .in(path[UUID]("novelId"))
      .out(jsonBody[Option[Save]])
      .errorOut(stringBody)

  val deleteSaveEndpoint: Endpoint[(UUID, UUID), String, Unit, Any] =
    baseEndpoint.delete
      .in("saves")
      .in(path[UUID]("userId"))
      .in(path[UUID]("novelId"))
      .out(jsonBody[Unit])
      .errorOut(stringBody)

  val allEndpoints: List[Endpoint[_, _, _, _]] =
    List(
      createSaveEndpoint,
      getSaveEndpoint,
      deleteSaveEndpoint
    )
}
