package controllers

import com.typesafe.scalalogging.LazyLogging
import javax.inject.{Inject, Provider}
import play.api.{Configuration, Environment, OptionalSourceMapper}
import play.api.http.DefaultHttpErrorHandler
import play.api.mvc.Results.{Forbidden, NotFound}
import play.api.mvc.{RequestHeader, Result}
import play.api.routing.Router

import scala.concurrent.Future

class CustomErrorHandler @Inject() (
                                     environment: Environment,
                                     config: Configuration,
                                     sourceMapper: OptionalSourceMapper,
                                     router: Provider[Router]
                                   )
  extends DefaultHttpErrorHandler(environment, config, sourceMapper, router)
    with LazyLogging
{

  override def onBadRequest(request: RequestHeader, message: String): Future[Result] = super.onBadRequest(request, message)

  override def onForbidden(request: RequestHeader, message: String): Future[Result] = {
    Future.successful(
      Forbidden("You're not allowed to access this resource.")
    )
  }

  override def onNotFound(request: RequestHeader, message: String): Future[Result] = {
    Future.successful(NotFound("Not found."))
  }
}
