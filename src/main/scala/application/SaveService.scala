package application

import domain.{Save, SaveRepository}
import domain.errors.AppError

import java.util.UUID

trait SaveService[F[_]] {
  def createSave(userId: UUID, novelId: UUID, node: UUID): F[Either[AppError, Unit]]
  def getSave(userId: UUID, novelId: UUID): F[Either[AppError, Save]]
  def deleteSave(userId: UUID, novelId: UUID): F[Either[AppError, Unit]]
}

class SaveServiceImpl[F[_]](saveRepository: SaveRepository[F]) extends SaveService[F] {

  override def createSave(userId: UUID, novelId: UUID, nodeId: UUID): F[Either[AppError, Unit]] = {
    val save = Save(userId, novelId, nodeId)
    saveRepository.create(save)
  }

  override def getSave(userId: UUID, novelId: UUID): F[Either[AppError, Save]] = {
    saveRepository.get(userId, novelId)
  }

  override def deleteSave(userId: UUID, novelId: UUID): F[Either[AppError, Unit]] = {
    saveRepository.delete(userId, novelId)
  }
}

object SaveService {
  def apply[F[_]](saveRepository: SaveRepository[F]): SaveService[F] =
    new SaveServiceImpl(saveRepository)
}
