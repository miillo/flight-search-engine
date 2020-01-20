package com.tass.flightsscrapper.scrapers.services

import com.tass.flightsscrapper.configuration.ApplicationProperties
import com.tass.flightsscrapper.models.{Comment, SourceModel}
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.{Element, ElementQuery}
import org.jsoup.Connection

class ScrapingService(appProperties: ApplicationProperties) {
  private val enricherService = new EnricherService
  private var browser = new JsoupBrowser {
    override def requestSettings(conn: Connection): Connection = conn.timeout(appProperties.siteTimeout)
  }

  /**
   * Retrieves airport comments from http site
   *
   * @param sourceModel model data
   * @return list with model comments
   */
  def getModelComments(sourceModel: SourceModel): List[Comment] = {
    var airportUrl = ""
    searchModel(sourceModel) match {
      case Some(element) => airportUrl = element
      case None => return null
    }
    scrapeSite(sourceModel, airportUrl)
  }

  /**
   * Looks for URL based on source model name
   * todo validate url with first part of full name!
   *
   * @param sourceModel airport / airline data
   * @return model site URL
   */
  private def searchModel(sourceModel: SourceModel): Option[String] = {
    try {
      val modelName = sourceModel.fullName.split("\\s")(0)

      val firstSearchResult = browser
        .get(appProperties.skytraxSite + modelName) >?> element(appProperties.firstResSelPath)
      firstSearchResult match {
        case Some(element) =>
          Some(element.attr("href"))
        case None =>
          println("No results found for airport: " + sourceModel.fullName + "[" + sourceModel.code + "]")
          None
      }
    } catch {
      case rto: Exception =>
        println("Exception for: " + sourceModel.fullName + "[" + sourceModel.code + "]. Cause: " + rto.getMessage)
        None
    }
  }

  /**
   * Scrapes model site
   *
   * @param sourceModel model data
   * @param modelUrl model site URL
   * @return list with comments
   */
  private def scrapeSite(sourceModel: SourceModel, modelUrl: String): List[Comment] = {
    //-Dhttps.protocols=TLSv1.2 in JVM args!
    val commentsElems = browser.get(modelUrl) >?> elements(appProperties.commentSection)
    var comments: ElementQuery[Element] = null
    commentsElems match {
      case Some(element) =>
        comments = element
      case None =>
        println("No comments found for airport: " + sourceModel.fullName + "[" + sourceModel.code + "]")
        return null
    }

    comments
      .map(comment => {
        val ratingStr = (comment >> allText("div.rating-10 > span:nth-child(1)"))
        var rating = 0
        if (ratingStr.isEmpty) {
          rating = -1
        } else {
          rating = ratingStr.toInt
        }
        val date = comment >> allText("div.body > h3.text_sub_header > time")
        val commentStr = comment >> allText("div.tc_mobile > div.text_content")
        val sentimentRate = enricherService.getStanfordSentimentRate(commentStr)
        Comment(sourceModel.fullName, sourceModel.code, rating, date, commentStr, sentimentRate)
      })
      .toList
  }
}
