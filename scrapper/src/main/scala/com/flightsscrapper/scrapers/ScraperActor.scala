package com.flightsscrapper.scrapers

import akka.actor.{Actor, Props}

object ScraperActor {
  def props: Props = Props[ScraperActor]
}

class ScraperActor extends Actor{
  override def receive: Receive = {
    case msg: String =>
      sender ! "Hello"
  }
}
