package domain

import errors.{SaveAlreadyExists, SaveNotFound}

import java.util.UUID

trait SaveRepository[F[_]] {
  def create(employee: Save): F[Either[SaveAlreadyExists, Unit]]

  def get(userId: UUID, novelId: UUID): F[Option[Save]]

  def update(
      userId: UUID,
      novelId: UUID,
      newNodeId: UUID
  ): F[Either[SaveNotFound, Unit]]

  def delete(userId: UUID, novelId: UUID): F[Either[SaveNotFound, Unit]]
}
