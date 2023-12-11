import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
//// import cats.effect.unsafe.implicits.global
//import cats.effect.{ExitCode, IO, IOApp}
//
//import scala.concurrent.ExecutionContext.Implicits.global
//import java.time.LocalTime
//import scala.concurrent.Future
//import scala.concurrent.duration.{DurationInt, SECONDS}
//import scala.language.postfixOps
//import scala.collection.immutable._
//
//object Main extends IOApp {
//  private def getCustomerByIdFuture(id: Int): Future[String] = {
//    Future {
//      println(s"Future function for $id evaluated at ${LocalTime.now()}")
//      // IO.sleep(20.seconds)
//      s"Customer_$id"
//    }
//  }
//
//  private def badGetCustomerByIdIo(id: Int): IO[String] = {
//    // val future = getCustomerByIdFuture(id)
//
//    val a = for {
//      customer <- IO.fromFuture(IO(getCustomerByIdFuture(id)))
//    } yield customer
//
//    a
//  }
//
//  override def run(args: List[String]): IO[ExitCode] = {
//
////    val program: IO[Unit] = for {
////      _ <- IO(println("Before sleep"))
////      _ <- IO.sleep(3.seconds)
////      _ <- IO(println("After sleep"))
////    } yield ()
////
////    program >> IO(ExitCode.Success)
//
//    val program = for {
//      customer <- badGetCustomerByIdIo(5)
//    } yield {
//      println(s"Function returned $customer, at ${LocalTime.now()}")
//      customer
//    }
//
//    program >> IO(ExitCode.Success)
//  }
//}

object Main extends App {
  val a = Future {
    println(5)
  }
}
