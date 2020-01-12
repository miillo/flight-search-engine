package com.flightsscrapper.supervisors

import akka.actor.{Actor, Props}
import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}
import com.flightsscrapper.configuration.ApplicationProperties
import com.flightsscrapper.models.{Airline, Airport}
import com.flightsscrapper.scrapers.ScraperActor
import com.flightsscrapper.supervisors.services.FileReader

object SupervisorActor {
  def props(appProperties: ApplicationProperties): Props = Props(new SupervisorActor(appProperties))
}

class SupervisorActor(appProperties: ApplicationProperties) extends Actor {

  val airports: List[Airport] = FileReader.readAirportsFile(appProperties.airportsFilePath)
  val airlines: List[Airline] = FileReader.readAirlinesFile(appProperties.airlinesFilePath)

  var router: Router = createRouter()
  router.route("Hello from master", self)

  override def receive: Receive = {
    case msg: String =>
      sender ! "Hello"
  }

  private def createRouter(): Router = {
    val routees = Vector.fill(appProperties.noOfScraperActors) {
      val r = context.actorOf(ScraperActor.props(appProperties))
      context.watch(r)
      ActorRefRoutee(r)
    }
    Router(RoundRobinRoutingLogic(), routees)
  }
}
