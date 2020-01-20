package com.tass.flightsscrapper.supervisors

import akka.actor.{Actor, Props}
import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}
import com.tass.flightsscrapper.configuration.ApplicationProperties
import com.tass.flightsscrapper.models.SourceModel
import com.tass.flightsscrapper.persistence.PersistenceActor
import com.tass.flightsscrapper.scrapers.ScraperActor
import com.tass.flightsscrapper.supervisors.services.FileReader

object SupervisorActor {
  def props(appProperties: ApplicationProperties): Props = Props(new SupervisorActor(appProperties))
}

class SupervisorActor(appProperties: ApplicationProperties) extends Actor {

  val airports: Seq[SourceModel] = FileReader.readAirportsFile(appProperties.airportsFilePath)
  val airlines: Seq[SourceModel] = FileReader.readAirlinesFile(appProperties.airlinesFilePath)

  var router: Router = createRouter()
  initializeAirports()
  initializeAirlines()

  override def receive: Receive = {
    case msg: String =>
      sender ! "Hello"
  }

  private def createRouter(): Router = {
    val persistenceActorRef = context.actorOf(PersistenceActor.props(appProperties))
    val routees = Vector.fill(appProperties.noOfScraperActors) {
      val r = context.actorOf(ScraperActor.props(appProperties, persistenceActorRef))
      context.watch(r)
      ActorRefRoutee(r)
    }
    Router(RoundRobinRoutingLogic(), routees)
  }

  private def initializeAirports(): Unit = {
    for (sourceModel <- airports) {
      router.route(sourceModel, self)
      Thread.sleep(appProperties.httpDelay)
    }
  }

  private def initializeAirlines(): Unit = {
    for (sourceModel <- airlines) {
      router.route(sourceModel, self)
      Thread.sleep(appProperties.httpDelay)
    }
  }
}
