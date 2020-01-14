package com.flightsscrapper.supervisors

import akka.actor.{Actor, Props}
import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}
import com.flightsscrapper.configuration.ApplicationProperties
import com.flightsscrapper.models.SourceModel
import com.flightsscrapper.scrapers.ScraperActor
import com.flightsscrapper.scrapers.services.ScrapingService
import com.flightsscrapper.supervisors.services.FileReader

object SupervisorActor {
  def props(appProperties: ApplicationProperties): Props = Props(new SupervisorActor(appProperties))
}

class SupervisorActor(appProperties: ApplicationProperties) extends Actor {

  val airports: List[SourceModel] = FileReader.readAirportsFile(appProperties.airportsFilePath)
  val airlines: List[SourceModel] = FileReader.readAirlinesFile(appProperties.airlinesFilePath)

  val test = new ScrapingService(appProperties)

  var router: Router = createRouter()
  router.route("Hello from master", self)

//  val test = new MongoDbService(appProperties)
//  test.saveInstances(Seq(Comment("my", "airport", 10,"10.01.10","test comment")))

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
