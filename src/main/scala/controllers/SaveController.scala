package controllers

import application.SaveService
import cats.implicits.{catsSyntaxEitherId, toFunctorOps}
import cats.{Applicative, Id}
import controllers.dto.getSave.GetSaveResponse
import controllers.errors.{
  ApiError,
  ConflictClientError,
  NotFoundClientError,
  ServerError
}
import sttp.tapir.server.ServerEndpoint
import tofu.Handle
import tofu.logging.Logging
import tofu.logging.Logging.Make
import tofu.syntax.handle._
import tofu.syntax.logging._

trait SaveController[F[_]] {
  def create: ServerEndpoint[Any, F]
  def get: ServerEndpoint[Any, F]
  def update: ServerEndpoint[Any, F]
  def delete: ServerEndpoint[Any, F]

  def getAllEndpoints: List[ServerEndpoint[Any, F]]
}

object SaveController {
  final private class Impl[F[_]: Applicative](saveService: SaveService[F])(
      implicit
      handle: Handle[F, Throwable],
      logging: Logging[F]
  ) extends SaveController[F] {

    override val create: ServerEndpoint[Any, F] =
      endpoints.createSaveEndpoint.serverLogic(createSaveRequest => {
        saveService
          .createSave(
            createSaveRequest.userId,
            createSaveRequest.novelId,
            createSaveRequest.nodeId
          )
          .map(_.left.map[ApiError](err => ConflictClientError(err.message)))
          .handle[Throwable](ex => {
            error"CreateSave exception: ${ex.getMessage}"
            ServerError("Unexpected Error").asLeft[Unit]
          })
      })

    override val get: ServerEndpoint[Any, F] =
      endpoints.getSaveEndpoint.serverLogic(getSaveRequest =>
        saveService
          .getSave(getSaveRequest.userId, getSaveRequest.novelId)
          .map(x => GetSaveResponse(x).asRight[ApiError])
          .handle[Throwable](ex => {
            error"GetSave exception: ${ex.getMessage}"
            ServerError("Unexpected Error").asLeft[GetSaveResponse]
          })
      )

    override val update: ServerEndpoint[Any, F] =
      endpoints.updateSaveEndpoint.serverLogic(updateSaveRequest =>
        saveService
          .updateSave(
            updateSaveRequest.userId,
            updateSaveRequest.novelId,
            updateSaveRequest.newNodeId
          )
          .map(_.left.map[ApiError](err => NotFoundClientError(err.message)))
          .handle[Throwable](ex => {
            error"UpdateSave exception: ${ex.getMessage}"
            ServerError("Unexpected Error").asLeft[Unit]
          })
      )

    override val delete: ServerEndpoint[Any, F] =
      endpoints.deleteSaveEndpoint.serverLogic(deleteSaveRequest =>
        saveService
          .deleteSave(deleteSaveRequest.userId, deleteSaveRequest.novelId)
          .map(_.left.map[ApiError](err => NotFoundClientError(err.message)))
          .handle[Throwable](ex => {
            error"DeleteSave exception: ${ex.getMessage}"
            ServerError("Unexpected Error").asLeft[Unit]
          })
      )

    override val getAllEndpoints: List[ServerEndpoint[Any, F]] =
      List(
        create,
        get,
        delete,
        update
      )
  }

  def make[F[_]: Applicative: Logging.Make](saveService: SaveService[F])(
      implicit handle: Handle[F, Throwable]
  ): SaveController[F] = {
    val logs: Make[F] = Logging.Make[F]
    implicit val logging: Id[Logging[F]] = logs.forService[SaveController[F]]
    new Impl(saveService)
  }
}
