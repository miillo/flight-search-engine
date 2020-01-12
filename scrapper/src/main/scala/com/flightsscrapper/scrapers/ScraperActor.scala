package com.flightsscrapper.scrapers

import akka.actor.{Actor, Props}
import com.flightsscrapper.configuration.ApplicationProperties
import com.flightsscrapper.scrapers.services.ScrapingService

object ScraperActor {
  def props(appProperties: ApplicationProperties): Props = Props(new ScraperActor(appProperties))
}

class ScraperActor(appProperties: ApplicationProperties) extends Actor{
  val scrapingService: ScrapingService = new ScrapingService(appProperties)

  override def receive: Receive = {
    case msg: String =>
      println("Kid: " + msg)
  }
}
