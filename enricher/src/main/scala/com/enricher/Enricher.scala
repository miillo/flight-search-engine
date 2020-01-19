package com.enricher

import com.flightsscrapper.configuration.ApplicationProperties
import com.flightsscrapper.persistence.services.MongoDbService

class Enricher(appProperties: ApplicationProperties) {
  val mongoDbService = new MongoDbService(appProperties)

  def enrich(): Unit = {
    val comments = mongoDbService.getElements("Cayman Airways")

  }
}
