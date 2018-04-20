package controllers

import java.util.Date

import com.typesafe.scalalogging.LazyLogging
import javax.inject._
import play.api.libs.json.Json
import play.api.mvc._
import views.html._

import scala.concurrent.ExecutionContext

object HomeController {
  case class Users(email: String, login: String, password: String, createdAt: Date)
  val user1 = Users(
    email = "admin@admin.com",
    login = "admin",
    password = "admin123",
    createdAt = new Date
  )
  val user2 = Users(
    email = "shoxrux0390.@mail.ru",
    login = "shoxrux123",
    password = "qwe41qa",
    createdAt = new Date
  )
  val user3 = Users(
    email = "shoxruxxudoynazarov0390.@mail.ru",
    login = "banana123",
    password = "shoxrux0390",
    createdAt = new Date
  )
  val user4 = Users(
    email = "xudoynazarov0390.@mail.ru",
    login = "banana0390",
    password = "fdsa123",
    createdAt = new Date
  )
  val user5 = Users(
    email = "java0390.@gmail.com",
    login = "scala123",
    password = "banana4556",
    createdAt = new Date
  )
  var usersList = List(user1, user2, user3, user4, user5)

  case class Student(name: String, group: String)
  var studentsList = List(
    Student("Maftunbek", "951-17"),
    Student("Shohrux", "ChangeMe"),
    Student("Sirojiddin", "ChangeMe"),
    Student("X.Shohrux", "ChangeMe"),
    Student("JavlonBek", "ChangeMe"),
    Student("Abdulla", "ChangeMe"),
    Student("Naima", "ChangeMe"),
    Student("Yulduz", "ChangeMe"),
    Student("Nizomiddin", "ChangeMe")
  )
  }


@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents,
                               indexTemplate: index,
                               tablesTemplate: tables,
                               loginTemplate: login,
                               usersTemplate: users,
                               not_foundTemplate: not_found,
                               signUpTemplate: sign_up)
                              (implicit val ec: ExecutionContext)
  extends BaseController with LazyLogging {
  import HomeController._
  val LoginSessionKey = "login.key"

  implicit val usersFormat = Json.format[Users]
  implicit val studentFormat = Json.format[Student]

  def index = Action { implicit  request => {
    val session = request.session.get(LoginSessionKey)
    Ok(indexTemplate(session))
  }}

  def tables = Action {
    Ok(tablesTemplate())
  }
  def users = Action { implicit  request => {
    request.session.get(LoginSessionKey).map{ session =>
    Ok(usersTemplate())
    }.getOrElse {
      Redirect(routes.HomeController.login()).flashing("error" -> "Please login to get users report.")
    }
  }}

  def login = Action { implicit request: RequestHeader => {
    Ok(loginTemplate())
  }}

  def not_found = Action {
    Ok(not_foundTemplate())
  }
  def logout = Action { implicit request => {
    Redirect(routes.HomeController.index()).withSession(
      request.session - LoginSessionKey
    )
  }}

  def report = Action { implicit  request => {
      request.session.get(LoginSessionKey).map{ _ =>
        Ok(Json.toJson(usersList))
      }.getOrElse {
        Redirect(routes.HomeController.login()).flashing("error" -> "Please login to get users report.")
      }
   }}

  def loginPost = Action { implicit request => {
    val formParam = request.body.asFormUrlEncoded
    val userLogin = formParam.get("login").headOption
    val password = formParam.get("pwd").headOption
    val authByLoginAndPwd = usersList.exists(user => user.login == userLogin.getOrElse("") && user.password == password.getOrElse("") )
    if (authByLoginAndPwd) {
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
    usersList = usersList :+ Users(email, login, pwd, new Date())
    Redirect(routes.HomeController.login())
  }}

  def getStudents() = Action { implicit  request => {
      Ok(Json.toJson(studentsList))
  }}

}

