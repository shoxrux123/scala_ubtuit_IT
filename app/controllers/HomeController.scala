package controllers

import com.typesafe.scalalogging.LazyLogging
import javax.inject._
import play.api.mvc._
import views.html._

import scala.concurrent.ExecutionContext


@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents,
                               indexTemplate: index,
                               tablesTemplate: tables,
                               loginTemplate: login)
                              (implicit val ec: ExecutionContext)
  extends BaseController with LazyLogging {

  val LoginSessionKey = "login.key"

  def index = Action { implicit  request => {
    request.session.get(LoginSessionKey).map{ session =>
      logger.info(s"session: $session")
      Ok(indexTemplate())
    }.getOrElse{
      Ok(loginTemplate())
    }
  }}

  def tables = Action {
    Ok(tablesTemplate())
  }

  def login = Action { implicit request: RequestHeader => {
    Ok(loginTemplate())
  }}

  def logout = Action { implicit request => {
    Redirect(routes.HomeController.index()).withSession(
      request.session - LoginSessionKey
    )
  }}

  def loginPost = Action { implicit request => {
    val formParam = request.body.asFormUrlEncoded
    val userLogin = formParam.get("login").headOption
    val password = formParam.get("pwd").headOption
    if (userLogin.contains("admin") && password.contains("admin123")) {
      Redirect(routes.HomeController.index()).addingToSession(LoginSessionKey -> userLogin.getOrElse(""))
    } else {
      Redirect(routes.HomeController.login()).flashing("error" -> "Something went wrong. Please try again.")
    }
  }}



}
