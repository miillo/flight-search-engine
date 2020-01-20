package com.tass.flightsscrapper.scrapers

import akka.actor.{Actor, ActorRef, Props}
import com.tass.flightsscrapper.configuration.ApplicationProperties
import com.tass.flightsscrapper.models.SourceModel
import com.tass.flightsscrapper.persistence.PersistenceActor.ModelComments
import com.tass.flightsscrapper.scrapers.services.ScrapingService

object ScraperActor {
  def props(appProperties: ApplicationProperties, persistenceActorRef: ActorRef): Props =
    Props(new ScraperActor(appProperties, persistenceActorRef))
}

class ScraperActor(appProperties: ApplicationProperties, persistenceActorRef: ActorRef) extends Actor{
  val scrapingService: ScrapingService = new ScrapingService(appProperties)

  override def receive: Receive = {
    case msg: SourceModel =>
      println("ScraperActor received msg from: " + sender().path.name)
      val comments = scrapingService.getModelComments(msg)
      if (comments != null) {
        persistenceActorRef ! ModelComments(comments)
      } else {
        println("Reading comments for: " + msg.fullName + "[" + msg.code + "]" + " failed")
      }
  }
}
