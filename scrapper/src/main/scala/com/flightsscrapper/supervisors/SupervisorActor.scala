package com.flightsscrapper.supervisors

import akka.actor.{Actor, Props}
import com.flightsscrapper.configuration.ApplicationProperties
import com.flightsscrapper.models.{Airline, Airport}
import com.flightsscrapper.supervisors.services.FileReader

object SupervisorActor {
  def props(appProperties: ApplicationProperties): Props = Props(new SupervisorActor(appProperties))
}

class SupervisorActor(appProperties: ApplicationProperties) extends Actor {
  import SupervisorActor._

  val airports: List[Airport] = FileReader.readAirportsFile(appProperties.airportsFilePath)
  val airlines: List[Airline] = FileReader.readAirlinesFile(appProperties.airlinesFilePath)
  println(airlines.mkString(" "))

  override def receive: Receive = {
    case msg: String =>
      sender ! "Hello"
  }
}
