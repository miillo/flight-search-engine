package com.flightsscrapper

import akka.actor.ActorSystem
import com.flightsscrapper.configuration.ApplicationProperties
import com.flightsscrapper.scrapers.services.EnricherService
import com.flightsscrapper.supervisors.SupervisorActor

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object FlightsScrapper {

  def main(args: Array[String]): Unit = {
    val appProperties = new ApplicationProperties("scrapper/src/resources/application.conf")

    implicit val system: ActorSystem = ActorSystem("FlightsScrapper")
    system.actorOf(SupervisorActor.props(appProperties), "SupervisorActor")
    Await.result(system.whenTerminated, Duration.Inf)
  }
}
