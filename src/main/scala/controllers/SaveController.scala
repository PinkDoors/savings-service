package controllers

import cats.Applicative
import cats.implicits.toFunctorOps
import domain.SaveService
import domain.errors.AppError
import sttp.tapir.server.ServerEndpoint

trait SaveController[F[_]] {
  def create: ServerEndpoint[Any, F]
  def get: ServerEndpoint[Any, F]
  def delete: ServerEndpoint[Any, F]

  def getAllEndpoints: List[ServerEndpoint[Any, F]]
//  def createTodo: ServerEndpoint[Any, F]
//
//  def all: List[ServerEndpoint[Any, F]]
}

object SaveController {
  final private class Impl[F[_]: Applicative](saveService: SaveService[F]) extends SaveController[F] {

    override val create: ServerEndpoint[Any, F] =
      endpoints.createSaveEndpoint.serverLogic((userId) =>
        saveService.createSave(userId._1, userId._2, userId._3).map(_.left.map[AppError](identity))
        //          storage.list.leftMapIn(identity[AppError])
      )

    override val get: ServerEndpoint[Any, F] =
      endpoints.getSaveEndpoint.serverLogic((userId) =>
        saveService.getSave(userId._1, userId._2).map(_.left.map[AppError](identity))
      )

    override val delete: ServerEndpoint[Any, F] =
      endpoints.deleteSaveEndpoint.serverLogic((userId) =>
        saveService.deleteSave(userId._1, userId._2).map(_.left.map[AppError](identity))
      )

    override val getAllEndpoints: List[ServerEndpoint[Any, F]] =
      List(
        create,
        get,
        delete
      )
  }

  def make[F[_]: Applicative](saveService: SaveService[F]): SaveController[F] = new Impl(saveService)
}
