package com.flightsscrapper.scrapers.services

import com.flightsscrapper.configuration.ApplicationProperties
import com.flightsscrapper.models.{Airport, Comment}
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._
import net.ruippeixotog.scalascraper.model.{Element, ElementQuery}

class ScrapingService(appProperties: ApplicationProperties) {

  private var browser = JsoupBrowser()

  /**
   * Retrieves airport comments from http site
   *
   * @param airport airport data
   * @return list with airport comments
   */
  def getAirportComments(airport: Airport): List[Comment] = {
    var airportUrl = ""
    searchAirport(airport) match {
      case Some(element) => airportUrl = element
      case None => None
    }

    scrapeAirportSite(airport, airportUrl)
  }

  /**
   * Looks for airport URL based on name
   * todo validate url with first part of full name!
   *
   * @param airport airport data
   * @return airport site URL
   */
  private def searchAirport(airport: Airport): Option[String] = {
    val firstSearchResult = browser
      .get(appProperties.skytraxSite + airport.fullName) >?> element(appProperties.firstResSelPath)
    firstSearchResult match {
      case Some(element) =>
        Some(element.attr("href"))
      case None =>
        println("No results found for airport: " + airport.fullName + "[" + airport.code + "]")
        None
    }
  }

  /**
   * Scrapes airport site
   * todo parse date
   *
   * @param airport airport data
   * @param airportUrl airport site URL
   * @return list with comments
   */
  private def scrapeAirportSite(airport: Airport, airportUrl: String): List[Comment] = {
    //-Dhttps.protocols=TLSv1.2 in JVM args!
    val commentsElems = browser.get(airportUrl) >?> elements(appProperties.commentSection)
    var comments: ElementQuery[Element] = null
    commentsElems match {
      case Some(element) =>
        comments = element
      case None =>
        println("No comments found for airport: " + airport.fullName + "[" + airport.code + "]")
        None
    }

    comments
      .map(comment => {
        val rating = (comment >> allText("div.rating-10 > span:nth-child(1)")).toInt
        val date = comment >> allText("div.body > h3.text_sub_header > time")
        val commentStr = comment >> allText("div.tc_mobile > div.text_content")
        Comment(rating, date, commentStr)
      })
      .toList
  }
}
