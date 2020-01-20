package com.tass.flightsscrapper.persistence

import akka.actor.{Actor, Props}
import com.tass.flightsscrapper.configuration.ApplicationProperties
import com.tass.flightsscrapper.models.Comment
import com.tass.flightsscrapper.persistence.PersistenceActor.ModelComments
import com.tass.flightsscrapper.persistence.services.MongoDbService

object PersistenceActor {
  def props(appProperties: ApplicationProperties): Props = Props(new PersistenceActor(appProperties))
  case class ModelComments(comments: List[Comment])
}

class PersistenceActor(appProperties: ApplicationProperties) extends Actor {
  val mongoDbService = new MongoDbService(appProperties)

  override def receive: Receive = {
    case msg: ModelComments =>
      println("PersistenceActor received msg from: " + sender().path.name)
      if (msg.comments.isEmpty) {
        println("Comments list empty!")
      } else {
        mongoDbService.saveInstances(msg.comments)
      }
  }
}
