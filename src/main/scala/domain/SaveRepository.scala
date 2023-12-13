package domain

import domain.errors.AppError

import java.util.UUID


trait SaveRepository[F[_]] {
  def create(employee: Save): F[Either[AppError, Unit]]

  def get(userId: UUID, novelId: UUID): F[Either[AppError, Save]]

  def delete(userId: UUID, novelId: UUID): F[Either[AppError, Unit]]
}
