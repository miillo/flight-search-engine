package com.flightsscrapper.persistence

import akka.actor.{Actor, Props}
import com.flightsscrapper.configuration.ApplicationProperties

object PersistenceActor {
  def props(appProperties: ApplicationProperties): Props = Props(new PersistenceActor(appProperties))
}

class PersistenceActor(appProperties: ApplicationProperties) extends Actor {
  override def receive: Receive = {
    case msg: String =>
      println("Kid: " + msg)
  }
}
