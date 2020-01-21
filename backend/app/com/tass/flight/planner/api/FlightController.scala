package com.tass.flight.planner.api

import com.tass.flight.planner.domain.{Airport, EnrichedFlight}
import com.tass.flight.planner.infrastructure.FlightDataProvider
import javax.inject.Inject
import play.api.libs.json.{Json, OFormat}
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext

class FlightController @Inject()(journeyPlanner: JourneyPlanner, cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  implicit val airportFormat: OFormat[Airport] = Json.format[Airport]
  implicit val planRequestFormat: OFormat[FlightPlanRequest] = Json.format[FlightPlanRequest]
  implicit val enrichedFlightFormat: OFormat[EnrichedFlight] = Json.format[EnrichedFlight]

  def airports = Action {
    Ok(Json.toJson(FlightDataProvider.airports))
  }

  def plan = Action(parse.json) { request =>
    request
      .body
      .validate[FlightPlanRequest]
      .map(request =>
        journeyPlanner
          .plan(request.start, request.destination)
          .map(journey => Ok(Json.toJson(journey)))
          .getOrElse(NotFound)
      )
      .getOrElse(BadRequest)
  }
}
