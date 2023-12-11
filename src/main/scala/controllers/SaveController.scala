package controllers

import sttp.tapir.server.http4s._
import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

class SaveController(saveService: SaveService) extends Http4sDsl[IO] {
  val saveRoutes: HttpRoutes[IO] =
    SaveEndpoints.allEndpoints.toRoutes {
      case (_, (userId, novelId, node)) => createSave(userId, novelId, node)
      case (_, (userId, novelId))        => getSave(userId, novelId)
      case (_, (userId, novelId))        => deleteSave(userId, novelId)
    }

  private def createSave(userId: UUID, novelId: UUID, node: UUID): IO[Either[String, Unit]] =
    saveService.createSave(userId, novelId, node).attempt.map {
      case Right(_)    => Right(())
      case Left(error) => Left(error.toString)
    }

  private def getSave(userId: UUID, novelId: UUID): IO[Option[Save]] =
    saveService.getSave(userId, novelId)

  private def deleteSave(userId: UUID, novelId: UUID): IO[Either[String, Unit]] =
    saveService.deleteSave(userId, novelId).attempt.map {
      case Right(_)    => Right(())
      case Left(error) => Left(error.toString)
    }
}

object SaveController {
  def apply(saveService: SaveService): SaveController =
    new SaveController(saveService)
}
