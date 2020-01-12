package com.flightsscrapper.scrapers.services

import com.flightsscrapper.configuration.ApplicationProperties
import com.flightsscrapper.models.Airport
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._

class ScrapingService(appProperties: ApplicationProperties) {

  private val browser = JsoupBrowser()

  def getAirportComments(airport: Airport): Unit = {
    var airportUrl = ""
    getAirportUrl(airport) match {
      case Some(element) => airportUrl = element
      case None => return
    }



  }

  private def getAirportUrl(airport: Airport): Option[String] = {
    val firstSearchResult = browser
      .get(appProperties.skytraxSite + airport.fullName) >?> element(appProperties.firstResSelPath)
    firstSearchResult match {
      case Some(element) =>
        Some(element.attr("href"))
      case None =>
        println("No results found for airport: " + airport.fullName + " with code: " + airport.code)
        None
    }
  }

}
