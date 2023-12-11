package domain

import java.util.UUID

final case class Save private (userId: UUID, novelId: UUID, node: UUID)

//object Employee {
//
//  def apply(firstName: String, lastName: String): Option[Employee] =
//    Try(firstName.head.toLower + lastName.substring(0, 5).toLowerCase).toOption
//      .map(id => Employee(id, firstName, lastName))
//
//}

//object SaveRepository {
//
//  type EmployeeRepository = Has[EmployeeRepository.Service]
//
//  trait Service {
//    def save(employee: Employee): IO[PersistenceFailure, Employee]
//    def get(id: String): IO[PersistenceFailure, Option[Employee]]
//    def getAll(): IO[PersistenceFailure, Seq[Employee]]
//    def delete(id: String): IO[PersistenceFailure, Unit]
//  }
//
//  sealed trait PersistenceFailure
//  object PersistenceFailure {
//    final case class UnexpectedPersistenceFailure(err: Throwable) extends PersistenceFailure
//  }
//
//  //   accessor methods
//  def save(
//            employee: Employee
//          ): ZIO[EmployeeRepository, PersistenceFailure, Employee] =
//    ZIO.accessM(_.get.save(employee))
//
//  def get(id: String): ZIO[EmployeeRepository, PersistenceFailure, Option[Employee]] =
//    ZIO.accessM(_.get.get(id))
//
//  def getAll(): ZIO[EmployeeRepository, PersistenceFailure, Seq[Employee]] =
//    ZIO.accessM(_.get.getAll())
//
//  def delete(id: String): ZIO[EmployeeRepository, PersistenceFailure, Unit] =
//    ZIO.accessM(_.get.delete(id))
//}