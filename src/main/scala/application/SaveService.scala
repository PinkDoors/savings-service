package application

import cats.implicits.{toFlatMapOps, toFunctorOps}
import cats.{Id, Monad}
import domain.errors.{SaveAlreadyExists, SaveNotFound}
import domain.{Save, SaveRepository}
import tofu.logging.Logging
import tofu.logging.Logging.Make
import tofu.syntax.logging._

import java.util.UUID

trait SaveService[F[_]] {
  def createSave(
      userId: UUID,
      novelId: UUID,
      node: UUID
  ): F[Either[SaveAlreadyExists, Unit]]
  def getSave(userId: UUID, novelId: UUID): F[Option[Save]]
  def updateSave(
      userId: UUID,
      novelId: UUID,
      newNodeId: UUID
  ): F[Either[SaveNotFound, Unit]]
  def deleteSave(userId: UUID, novelId: UUID): F[Either[SaveNotFound, Unit]]
}

object SaveService {

  private final class Impl[F[_]](saveRepository: SaveRepository[F])
      extends SaveService[F] {

    override def createSave(
        userId: UUID,
        novelId: UUID,
        nodeId: UUID
    ): F[Either[SaveAlreadyExists, Unit]] = {
      val save = Save(userId, novelId, nodeId)
      saveRepository.create(save)
    }

    override def getSave(
        userId: UUID,
        novelId: UUID
    ): F[Option[Save]] = {
      saveRepository.get(userId, novelId)
    }

    override def updateSave(
        userId: UUID,
        novelId: UUID,
        newNodeId: UUID
    ): F[Either[SaveNotFound, Unit]] = {
      saveRepository.update(userId, novelId, newNodeId)
    }

    override def deleteSave(
        userId: UUID,
        novelId: UUID
    ): F[Either[SaveNotFound, Unit]] = {
      saveRepository.delete(userId, novelId)
    }
  }

  private final class LoggingImpl[F[_]: Monad](service: SaveService[F])(implicit
      logging: Logging[F]
  ) extends SaveService[F] {

    override def createSave(
        userId: UUID,
        novelId: UUID,
        nodeId: UUID
    ): F[Either[SaveAlreadyExists, Unit]] = for {
      result <- service.createSave(userId, novelId, nodeId)
      _ <- result match {
        case Right(_) => info"CreateSave success."
        case Left(error) => info"CreateSave error: ${error.message}"
      }
    } yield result

    override def getSave(userId: UUID, novelId: UUID): F[Option[Save]] = for {
      result <- service.getSave(userId, novelId)
      _ <- info"GetSave success."
    } yield result

    override def updateSave(
        userId: UUID,
        novelId: UUID,
        newNodeId: UUID
    ): F[Either[SaveNotFound, Unit]] = for {
      result <- service.updateSave(userId, novelId, newNodeId)
      _ <- result match {
        case Right(_)    => info"UpdateSave success."
        case Left(error) => info"UpdateSave error: ${error.message}"
      }
    } yield result

    override def deleteSave(
        userId: UUID,
        novelId: UUID
    ): F[Either[SaveNotFound, Unit]] = for {
      result <- service.deleteSave(userId, novelId)
      _ <- result match {
        case Right(_)    => info"DeleteSave success."
        case Left(error) => info"DeleteSave error: ${error.message}"
      }
    } yield result
  }

  def make[F[_]: Monad: Logging.Make](
      saveRepository: SaveRepository[F]
  ): SaveService[F] = {
    val logs: Make[F] = Logging.Make[F]
    implicit val logging: Id[Logging[F]] = logs.forService[SaveService[F]]
    val impl = new Impl(saveRepository)
    new LoggingImpl(impl)
  }
}
