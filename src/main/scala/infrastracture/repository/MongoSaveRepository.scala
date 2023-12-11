package infrastracture.repository

import scala.concurrent.ExecutionContext.Implicits.global
import cats.effect.IO
import domain.{Save, SaveRepository}
import domain.errors.{AppError, InternalError, SaveAlreadyExists, SaveNotFound}
import org.mongodb.scala.model.Filters.{and, equal}
import org.mongodb.scala.model.Updates.set
import org.mongodb.scala.model.{IndexModel, IndexOptions}
import org.mongodb.scala.result.InsertOneResult
import org.mongodb.scala.{
  DuplicateKeyException,
  MongoClient,
  MongoCollection,
  MongoCredential,
  MongoDatabase,
  ServerAddress
}

import java.util.UUID
import scala.concurrent.Future

class MongoSaveRepository(database: MongoDatabase)
    extends domain.SaveRepository {

  private val saves: MongoCollection[Save] = database.getCollection("saves")

  // Ensure that the required indexes are created on startup
  saves.createIndexes(
    Seq(
      IndexModel(equal("userId", 1), IndexOptions().unique(true)),
      IndexModel(equal("novelId", 1), IndexOptions().unique(true))
    )
  )

  override def create(save: Save): Future[Either[AppError, Unit]] = {
    val result = saves.insertOne(save).toFuture()

    result.map(_ => Right()).recover[Either[AppError, Unit]] {
      case _: DuplicateKeyException => Left(SaveAlreadyExists())
      case ex                       => Left(InternalError(ex))
    }
  }

  override def get(userId: UUID, novelId: UUID): Future[Option[Save]] = {
    saves
      .find(and(equal("userId", userId), equal("novelId", novelId)))
      .headOption()
  }

  override def delete(
      userId: UUID,
      novelId: UUID
  ): Future[Either[AppError, Unit]] = {
    val result = saves
      .deleteOne(and(equal("userId", userId), equal("novelId", novelId)))
      .toFuture()

    result.map[Either[AppError, Unit]](x => {
      if (x.getDeletedCount > 0) {
        Right()
      } else {
        Left(SaveNotFound(userId, novelId))
      }
    })
  }
}

object MongoSaveRepository {
  def apply(database: MongoDatabase): MongoSaveRepository =
    new MongoSaveRepository(database)
}
