package application

import domain.Save
import domain.errors.{SaveAlreadyExists, SaveNotFound}

import java.util.UUID

trait SaveService[F[_]] {
  def createSave(userId: UUID, novelId: UUID, node: UUID): F[Either[SaveAlreadyExists, Unit]]
  def getSave(userId: UUID, novelId: UUID): F[Option[Save]]
  def updateSave(userId: UUID, novelId: UUID, newNodeId:UUID): F[Either[SaveNotFound, Unit]]
  def deleteSave(userId: UUID, novelId: UUID): F[Either[SaveNotFound, Unit]]
}
