package controllers

import java.util.Date

import com.typesafe.scalalogging.LazyLogging
import javax.inject._
import play.api.libs.json.Json
import play.api.mvc._
import views.html._

import scala.concurrent.ExecutionContext

object HomeController {
  case class Users(email: String, login: String, createdAt: Date)
  val user1 = Users(
    email = "asdf@asd.dd",
    login = "admin",
    createdAt = new Date
  )
  val user2 = Users(
    email = "shoxrux0390.@mail.ru",
    login = "shoxrux123",
    createdAt = new Date
  )
  val user3 = Users(
    email = "shoxruxxudoynazarov0390.@mail.ru",
    login = "banana123",
    createdAt = new Date
  )
  val user4 = Users(
    email = "xudoynazarov0390.@mail.ru",
    login = "banana0390",
    createdAt = new Date
  )
  val user5 = Users(
    email = "java0390.@gmail.com",
    login = "scala123",
    createdAt = new Date
  )
  val user6 = Users(
    email = "scala.@gmail.com",
    login = "scala321",
    createdAt = new Date
  )
  val user7 = Users(
    email = "javascala931.@mail.com",
    login = "person931",
    createdAt = new Date
  )
  val user8 = Users(
    email = "banana0390.@bk.ru",
    login = "apple123",
    createdAt = new Date
  )
  val user9 = Users(
    email = "apple931.@mail.ru",
    login = "cherry",
    createdAt = new Date
  )
  val user10 = Users(
    email = "person.@gmail.com",
    login = "user123",
    createdAt = new Date
  )
  var usersList = List(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10)
  }


@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents,
                               indexTemplate: index,
                               tablesTemplate: tables,
                               loginTemplate: login,
                               signUpTemplate: sign_up)
                              (implicit val ec: ExecutionContext)
  extends BaseController with LazyLogging {
  import HomeController._
  val LoginSessionKey = "login.key"

  implicit val usersFormat = Json.format[Users]

  def index = Action { implicit  request => {
    val session = request.session.get(LoginSessionKey)
      Ok(indexTemplate(session))
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

  def report = Action { implicit  request => {
    request.session.get(LoginSessionKey).map{ session =>
        Ok(Json.toJson(usersList))
      }.getOrElse {
        Redirect(routes.HomeController.login()).flashing("error" -> "Please login to get users report.")
      }
    }}

  def loginPost = Action { implicit request => {
    val formParam = request.body.asFormUrlEncoded
    val userLogin = formParam.get("login").headOption
    val password = formParam.get("pwd").headOption
    val checkLoginDefining = usersList.exists(_.login == userLogin.getOrElse(""))
    if (checkLoginDefining && password.contains("admin123")) {
      Redirect(routes.HomeController.index()).addingToSession(LoginSessionKey -> userLogin.getOrElse(""))
    } else {
      Redirect(routes.HomeController.login()).flashing("error" -> "Your login or password is incorrect.")
    }
  }}

  def showSignUpPage = Action {
    Ok(signUpTemplate())
  }

  def signUp =  Action { implicit request => {
    val formParam = request.body.asFormUrlEncoded
    val email = formParam.get("email").head
    val login = formParam.get("login").head
    val pwd = formParam.get("pwd").head
    usersList = usersList :+ Users(email, login, new Date())
    Ok("Successfully user added!")
  }}
}
