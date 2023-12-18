import application.SaveService
import cats.effect.{ExitCode, IO, IOApp, Resource}
import com.comcast.ip4s.{IpLiteralSyntax, Ipv4Address, Port}
import config.AppConfig
import controllers.SaveController
import infrastructure.repository.MongoSaveRepository
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Router
import sttp.apispec.openapi.circe.yaml._
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.swagger.SwaggerUI
import tofu.logging.Logging

object Main extends IOApp {

  private type App[A] = IO[A]

  private val mainLogs =
    Logging.Make.plain[IO].byName("Main")
  implicit val logMake: Logging.Make[IO] = Logging.Make.plain[IO]

  override def run(args: List[String]): IO[ExitCode] =
    (for {
      _ <- Resource.eval(mainLogs.info("Starting saving service...."))
      config <- Resource.eval(AppConfig.load)

      saveRepository = MongoSaveRepository[App](config.db)
      saveService = SaveService.make[App](saveRepository)
      controller = SaveController.make[App](saveService)

      openApi = OpenAPIDocsInterpreter()
        .toOpenAPI(
          es = controller.getAllEndpoints.map(_.endpoint),
          "savings-service",
          "1.0"
        )
        .toYaml

      routes = Http4sServerInterpreter[IO]().toRoutes(
        controller.getAllEndpoints ++ SwaggerUI[IO](openApi)
      )
      httpApp = Router("/" -> routes).orNotFound
      _ <- EmberServerBuilder
        .default[IO]
        .withHost(
          Ipv4Address.fromString(config.server.host).getOrElse(ipv4"0.0.0.0")
        )
        .withPort(Port.fromInt(8080).getOrElse(port"8080"))
        .withHttpApp(httpApp)
        .build
    } yield ()).useForever.as(ExitCode.Success)
}
