package com.tass.flightsscrapper

import akka.actor.ActorSystem
import com.tass.flightsscrapper.configuration.ApplicationProperties
import com.tass.flightsscrapper.supervisors.SupervisorActor

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object FlightsScrapper {

  def main(args: Array[String]): Unit = {
    val appProperties = new ApplicationProperties

    implicit val system: ActorSystem = ActorSystem("FlightsScrapper")
    system.actorOf(SupervisorActor.props(appProperties), "SupervisorActor")
    Await.result(system.whenTerminated, Duration.Inf)
  }
}
