package domain

import cats.effect.IO
import scala.concurrent.ExecutionContext.Implicits.global
import domain.errors.{AppError, SaveAlreadyExists, SaveNotFound}

import java.util.UUID

trait SaveService {
  def createSave(userId: UUID, novelId: UUID, node: UUID): IO[Either[AppError, Unit]]
  def getSave(userId: UUID, novelId: UUID): IO[Option[Save]]
  def deleteSave(userId: UUID, novelId: UUID): IO[Either[AppError, Unit]]
}

class SaveServiceImpl(saveRepository: SaveRepository) extends SaveService {

  override def createSave(userId: UUID, novelId: UUID, node: UUID): IO[Either[AppError, Unit]] = {
    val save = Save(userId, novelId, node)
    for {
      saveResult <- IO.fromFuture(IO(saveRepository.create(save)))
    } yield saveResult
  }

  override def getSave(userId: UUID, novelId: UUID): IO[Option[Save]] = {
    for {
      getResult <- IO.fromFuture(IO(saveRepository.get(userId, novelId)))
    } yield getResult
  }

  override def deleteSave(userId: UUID, novelId: UUID): IO[Either[AppError, Unit]] = {
    for {
      getResult <- IO.fromFuture(IO(saveRepository.delete(userId, novelId)))
    } yield getResult
  }
}

object SaveService {
  def apply(saveRepository: SaveRepository): SaveService =
    new SaveServiceImpl(saveRepository)
}
