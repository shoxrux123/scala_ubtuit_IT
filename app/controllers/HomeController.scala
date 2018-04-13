package controllers

import java.util.Date

import com.typesafe.scalalogging.LazyLogging
import javax.inject._
import play.api.libs.json.Json
import play.api.mvc._
import views.html._

import scala.concurrent.{ExecutionContext, Future}




@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents,
                               indexTemplate: index,
                               tablesTemplate: tables,
                               loginTemplate: login,
                               usersTemplate: users,
                               signUpTemplate: sign_up)
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
  }
  }
  def logout = Action { implicit request => {
    Redirect(routes.HomeController.index()).withSession(
      request.session - LoginSessionKey
    )
  }}
  def showSignUpPage = Action {
      Ok(signUpTemplate())
    }

  def users = Action {
    Ok(usersTemplate())
  }
  def loginPost = Action { implicit request => {
    val formParam = request.body.asFormUrlEncoded
    val userLogin = formParam.get("login").headOption
    val password = formParam.get("pwd").headOption
    if (userLogin.contains("admin") && password.contains("admin123")) {
      Redirect(routes.HomeController.index()).addingToSession(LoginSessionKey -> userLogin.getOrElse(""))
    } else {
      Redirect(routes.HomeController.login()).flashing("error" -> "Something went wrong. Please try again.")
    }
  }
  }


  def signUp =  Action { implicit request => {
    val formParam = request.body.asFormUrlEncoded
    val email = formParam.get("email").head
    val login = formParam.get("login").head
    val pwd = formParam.get("pwd").head
    logger.info(s"email: $email")
    logger.info(s"login: $login")
    logger.info(s"psw: $pwd")
    Ok("Successfully user added!")
  }}
}







