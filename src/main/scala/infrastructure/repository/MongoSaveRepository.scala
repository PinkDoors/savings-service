package infrastructure.repository

import cats.effect.Async
import cats.effect.kernel.Sync
import cats.syntax.all._
import config.DbConfig
import controllers.errors.ApiError
import domain.errors.{SaveAlreadyExists, SaveNotFound}
import domain.{Save, SaveRepository}
import mongo4cats.circe._
import mongo4cats.client.MongoClient
import mongo4cats.operations.{Filter, Update}

import java.util.UUID

class MongoSaveRepository[F[_]: Async](config: DbConfig)
    extends SaveRepository[F] {
  override def create(save: Save): F[Either[SaveAlreadyExists, Unit]] = {
    MongoClient.fromConnectionString[F](config.uri).use { client =>
      for {
        db <- client.getDatabase(config.dbName)
        coll <- db.getCollectionWithCodec[Save](config.dbSaveCollection)
        findResult <- coll.find
          .filter(
            Filter.eq("userId", save.userId.toString) && Filter
              .eq("novelId", save.novelId.toString)
          )
          .limit(1)
          .all
        createResult <- findResult.headOption match {
          case Some(_) => Sync[F].pure(SaveAlreadyExists(save.userId, save.novelId).asLeft[Unit])
          case None =>
            coll.insertOne(save).map(x => ().asRight[SaveAlreadyExists])
        }
      } yield createResult
    }
  }

  override def get(userId: UUID, novelId: UUID): F[Option[Save]] = {
    val findResult =
      MongoClient.fromConnectionString[F](config.uri).use { client =>
        for {
          db <- client.getDatabase(config.dbName)
          coll <- db.getCollectionWithCodec[Save](config.dbSaveCollection)
          findResult <- coll.find
            .filter(
              Filter.eq("userId", userId.toString) && Filter.eq("novelId", novelId.toString)
            )
            .limit(1)
            .all
        } yield findResult
      }

    findResult.map(_.headOption)
  }

  override def update(
      userId: UUID,
      novelId: UUID,
      newNodeId: UUID
  ): F[Either[SaveNotFound, Unit]] = {
    val findAndUpdateResult =
      MongoClient.fromConnectionString[F](config.uri).use { client =>
        for {
          db <- client.getDatabase(config.dbName)
          coll <- db.getCollectionWithCodec[Save](config.dbSaveCollection)
          findAndUpdateResult <- coll.findOneAndUpdate(
            Filter.eq("userId", userId.toString) && Filter
              .eq("novelId", novelId.toString),
            Update.set("nodeId", newNodeId.toString)
          )
        } yield findAndUpdateResult
      }

    findAndUpdateResult.map {
      case Some(_) => ().asRight[SaveNotFound]
      case None    => SaveNotFound(userId, novelId).asLeft[Unit]
    }
  }

  override def delete(
      userId: UUID,
      novelId: UUID
  ): F[Either[SaveNotFound, Unit]] = {
    val deleteResult =
      MongoClient.fromConnectionString[F](config.uri).use { client =>
        for {
          db <- client.getDatabase(config.dbName)
          coll <- db.getCollectionWithCodec[Save](config.dbSaveCollection)
          deleteResult <- coll.deleteOne(
            Filter.eq("userId", userId.toString) && Filter
              .eq("novelId", novelId.toString)
          )
        } yield deleteResult
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
