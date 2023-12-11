package domain

import cats.effect.IO
import domain.errors.{AppError, SaveAlreadyExists, SaveNotFound}

import java.util.UUID


trait SaveRepository {
  def create(employee: Save): IO[Either[AppError, Unit]]

  def get(userId: UUID, novelId: UUID): IO[Option[Save]]

  def delete(userId: UUID, novelId: UUID): IO[Either[AppError, Unit]]
}
