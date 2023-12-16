package controllers

import application.SaveService
import cats.Applicative
import cats.implicits.{catsSyntaxEitherId, toFunctorOps}
import controllers.dto.getSave.GetSaveResponse
import controllers.errors.{ApiError, ConflictClientError, NotFoundClientError, ServerError}
import domain.Save
import sttp.tapir.server.ServerEndpoint
import tofu.Handle
import tofu.syntax.handle._

trait SaveController[F[_]] {
  def create: ServerEndpoint[Any, F]
  def get: ServerEndpoint[Any, F]
  def delete: ServerEndpoint[Any, F]

  def getAllEndpoints: List[ServerEndpoint[Any, F]]
}

object SaveController {
  final private class Impl[F[_]: Applicative](saveService: SaveService[F])(
      implicit handle: Handle[F, Throwable]
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
          // TODO: Add logging
          .handle[Throwable](ex => {
            println(ex.getMessage)
            ServerError("Unexpected Error").asLeft[Unit]
          })
      })

    override val get: ServerEndpoint[Any, F] =
      endpoints.getSaveEndpoint.serverLogic(getSaveRequest =>
        saveService
          .getSave(getSaveRequest.userId, getSaveRequest.novelId)
          .map(x => GetSaveResponse(x).asRight[ApiError])
          // TODO: Add logging
          .handle[Throwable](ex => {
            println(ex.getMessage)
            ServerError("Unexpected Error").asLeft[GetSaveResponse]
          })
      )

    override val delete: ServerEndpoint[Any, F] =
      endpoints.deleteSaveEndpoint.serverLogic(deleteSaveRequest =>
        saveService
          .deleteSave(deleteSaveRequest.userId, deleteSaveRequest.novelId)
          .map(_.left.map[ApiError](err => NotFoundClientError(err.message)))
          .handle[Throwable](ex => {
            println(ex.getMessage)
            ServerError("Unexpected Error").asLeft[Unit]
          })
      )

    override val getAllEndpoints: List[ServerEndpoint[Any, F]] =
      List(
        create,
        get,
        delete
      )
  }

  def make[F[_]: Applicative](saveService: SaveService[F])(implicit
      handle: Handle[F, Throwable]
  ): SaveController[F] =
    new Impl(saveService)
}
