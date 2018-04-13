package controllers

import java.util.Date

import com.typesafe.scalalogging.LazyLogging
import javax.inject._
import play.api.libs.json.Json
import play.api.mvc._
import views.html._

import scala.concurrent.{ExecutionContext, Future}

object HomeController {
  case class Users(email: String, login: String, createdAt: Date)
  var UsersList = List.empty[Users]
  val user1 = Users(
    email = "asdf@asd.dd",
    login = "asdf",
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

  def index = Action { implicit  request => {
    request.session.get(LoginSessionKey).map{ session =>
      logger.info(s"session: $session")
      Ok(indexTemplate())
    }.getOrElse {
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
  }
  }

//  def report = Action {
//    val usersList = List(user1,user2,user3,user4,user5,user6,user7,user8,user9,user10)
//    usersList.map{ users =>
//      Ok(Json.toJson(Map("users" -> users)))
//    }
//  }


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

  def showSignUpPage = Action {
    Ok(signUpTemplate())
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
