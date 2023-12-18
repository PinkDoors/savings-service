package infrastructure.repository

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import com.dimafeng.testcontainers.{ForEachTestContainer, MongoDBContainer}
import config.DbConfig
import domain.Save
import domain.errors.{SaveAlreadyExists, SaveNotFound}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

import java.util.UUID

class MongoSaveRepositorySpec extends AnyFlatSpec with ForEachTestContainer {
  override val container: MongoDBContainer = new MongoDBContainer()
  def dbConfig = new DbConfig(
    uri = container.container.getConnectionString,
    dbName = "user-novels-savings",
    dbSaveCollection = "savings"
  )

  "create" should "return SaveAlreadyExists for not existing save" in {
    val userUuid = UUID.fromString("3fa85f64-5717-4262-b3fc-2c963f66afa6")
    val novelUuid = UUID.fromString("4fa85f64-5717-4262-b3fc-2c963f66afa6")
    val nodeUuid = UUID.fromString("5fa85f64-5717-4262-b3fc-2c963f66afa6")
    val save = Save(userUuid, novelUuid, nodeUuid)

    val mongoRepo = new MongoSaveRepository[IO](dbConfig)
    mongoRepo.create(save).unsafeRunSync()

    val secondCreateResult = mongoRepo.create(save).unsafeRunSync()

    val exp = Left(SaveAlreadyExists(userUuid, novelUuid))

    exp shouldEqual secondCreateResult
  }

  it should "create record and return Unit for existing save" in {
    val userUuid = UUID.fromString("3fa85f64-5717-4262-b3fc-2c963f66afa6")
    val novelUuid = UUID.fromString("4fa85f64-5717-4262-b3fc-2c963f66afa6")
    val nodeUuid = UUID.fromString("5fa85f64-5717-4262-b3fc-2c963f66afa6")
    val save = Save(userUuid, novelUuid, nodeUuid)

    val mongoRepo = new MongoSaveRepository[IO](dbConfig)
    val createResult = mongoRepo.create(save).unsafeRunSync()
    val getResult = mongoRepo.get(userUuid, novelUuid).unsafeRunSync()

    val exp = Left(SaveAlreadyExists(userUuid, novelUuid))

    val expDeleteResult = Right(())
    val expGetResult = Some(save)

    expDeleteResult shouldEqual createResult
    expGetResult shouldEqual getResult
  }

  "get" should "return None for not existing save" in {
    val userUuid = UUID.fromString("3fa85f64-5717-4262-b3fc-2c963f66afa6")
    val novelUuid = UUID.fromString("3fa85f64-5717-4262-b3fc-2c963f66afa6")

    val mongoRepo = new MongoSaveRepository[IO](dbConfig)
    val getResult = mongoRepo.get(userUuid, novelUuid).unsafeRunSync()

    val exp = None

    exp shouldEqual getResult
  }

  it should "return existing save" in {
    val userUuid = UUID.fromString("3fa85f64-5717-4262-b3fc-2c963f66afa6")
    val novelUuid = UUID.fromString("4fa85f64-5717-4262-b3fc-2c963f66afa6")
    val nodeUuid = UUID.fromString("5fa85f64-5717-4262-b3fc-2c963f66afa6")
    val save = Save(userUuid, novelUuid, nodeUuid)

    val mongoRepo = new MongoSaveRepository[IO](dbConfig)
    mongoRepo.create(save).unsafeRunSync()

    val getResult = mongoRepo.get(userUuid, novelUuid).unsafeRunSync()

    val exp = Some(Save(userUuid, novelUuid, nodeUuid))

    exp shouldEqual getResult
  }

  "update" should "return SaveNotFound for not existing save" in {
    val userUuid = UUID.fromString("3fa85f64-5717-4262-b3fc-2c963f66afa6")
    val novelUuid = UUID.fromString("4fa85f64-5717-4262-b3fc-2c963f66afa6")
    val nodeUuid = UUID.fromString("5fa85f64-5717-4262-b3fc-2c963f66afa6")

    val mongoRepo = new MongoSaveRepository[IO](dbConfig)
    val updateResult = mongoRepo.update(userUuid, novelUuid, nodeUuid).unsafeRunSync()

    val exp = Left(SaveNotFound(userUuid, novelUuid))

    exp shouldEqual updateResult
  }

  it should "return update nodeId and return Unit for existing save" in {
    val userUuid = UUID.fromString("3fa85f64-5717-4262-b3fc-2c963f66afa6")
    val novelUuid = UUID.fromString("4fa85f64-5717-4262-b3fc-2c963f66afa6")
    val nodeUuid = UUID.fromString("5fa85f64-5717-4262-b3fc-2c963f66afa6")
    val save = Save(userUuid, novelUuid, nodeUuid)

    val mongoRepo = new MongoSaveRepository[IO](dbConfig)
    mongoRepo.create(save).unsafeRunSync()

    val newNodeUuid = UUID.fromString("6fa85f64-5717-4262-b3fc-2c963f66afa6")
    val updateResult = mongoRepo.update(userUuid, novelUuid, newNodeUuid).unsafeRunSync()
    val getResult = mongoRepo.get(userUuid, novelUuid).unsafeRunSync()

    val expUpdateResult = Right(())
    val expNodeUuid = newNodeUuid

    expUpdateResult shouldEqual updateResult
    expNodeUuid shouldEqual getResult.get.nodeId
  }

  "delete" should "return SaveNotFound for not existing save" in {
    val userUuid = UUID.fromString("3fa85f64-5717-4262-b3fc-2c963f66afa6")
    val novelUuid = UUID.fromString("4fa85f64-5717-4262-b3fc-2c963f66afa6")

    val mongoRepo = new MongoSaveRepository[IO](dbConfig)
    val deleteResult = mongoRepo.delete(userUuid, novelUuid).unsafeRunSync()

    val exp = Left(SaveNotFound(userUuid, novelUuid))

    exp shouldEqual deleteResult
  }

  it should "delete record and return Unit for existing save" in {
    val userUuid = UUID.fromString("3fa85f64-5717-4262-b3fc-2c963f66afa6")
    val novelUuid = UUID.fromString("4fa85f64-5717-4262-b3fc-2c963f66afa6")
    val nodeUuid = UUID.fromString("5fa85f64-5717-4262-b3fc-2c963f66afa6")
    val save = Save(userUuid, novelUuid, nodeUuid)

    val mongoRepo = new MongoSaveRepository[IO](dbConfig)
    mongoRepo.create(save).unsafeRunSync()

    val deleteResult = mongoRepo.delete(userUuid, novelUuid).unsafeRunSync()
    val getResult = mongoRepo.get(userUuid, novelUuid).unsafeRunSync()

    val expDeleteResult = Right(())
    val expGetResult = None

    expDeleteResult shouldEqual deleteResult
    expGetResult shouldEqual getResult
  }
}