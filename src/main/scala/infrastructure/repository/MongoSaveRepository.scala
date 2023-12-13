package infrastructure.repository

import cats.effect.Async
import cats.syntax.all._
import config.DbConfig
import mongo4cats.client.MongoClient
import mongo4cats.circe._
import mongo4cats.operations.Filter
import domain.{Save, SaveRepository}
import domain.errors.{AppError, InternalError, SaveAlreadyExists, SaveNotFound}
import tofu.syntax.feither.EitherFOps

import java.util.UUID

class MongoSaveRepository[F[_]: Async](config: DbConfig)
    extends SaveRepository[F] {

  override def create(save: Save): F[Either[AppError, Unit]] = {
    def createSave = MongoClient.fromConnectionString[F]("mongodb://localhost:27017").use {
      client =>
        for {
          db <- client.getDatabase(config.mongoDbName)
          coll <- db.getCollectionWithCodec[Save](config.mongoDbSaveCollection)
          createResult <- coll.insertOne(save)
        } yield createResult
    }

    val existingSave = get(save.userId, save.novelId)
    existingSave.map {
      case Right(_) => Left(SaveAlreadyExists())
      case Left(error) =>
        error match {
          case _: SaveNotFound => Right
          case otherErrors => otherErrors
        }
      }.flatMap(_ => createSave)
      .attempt
      .leftMapIn(ex => Left(InternalError(ex)))
      .map(_ => Right())
  }

  override def get(userId: UUID, novelId: UUID): F[Either[AppError, Save]] = {
    val findResult = MongoClient.fromConnectionString[F]("mongodb://localhost:27017").use {
      client =>
        for {
          db <- client.getDatabase(config.mongoDbName)
          coll <- db.getCollectionWithCodec[Save](config.mongoDbSaveCollection)
          findResult <- coll.find
            .filter(
              Filter.eq("userId", 10) && Filter.eq("novelId", "doc-[1-9]0")
            )
            .limit(1)
            .all
        } yield findResult
    }

    findResult.map(_.headOption)
      .attempt
      .leftMapIn(err => InternalError(err))
      .flatMapIn(_.toRight(SaveNotFound(userId, novelId)))
  }

  override def delete(userId: UUID, novelId: UUID): F[Either[AppError, Unit]] = {
    val deleteResult = MongoClient.fromConnectionString[F]("mongodb://localhost:27017").use {
      client =>
        for {
          db <- client.getDatabase(config.mongoDbName)
          coll <- db.getCollectionWithCodec[Save](config.mongoDbSaveCollection)
          findResult <- coll.deleteOne(Filter.eq("userId", 10) && Filter.eq("novelId", "doc-[1-9]0"))
        } yield findResult
    }

    deleteResult.map { result =>
      if (result.getDeletedCount > 0) {
        Right()
      } else {
        Left(SaveNotFound(userId, novelId))
      }
    }
  }
}

object MongoSaveRepository {
  def apply[F[_]: Async](
      config: DbConfig
  ): MongoSaveRepository[F] = {
    new MongoSaveRepository[F](config)
  }
}
