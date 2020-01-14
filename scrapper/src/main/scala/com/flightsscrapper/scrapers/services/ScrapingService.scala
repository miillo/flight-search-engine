package com.flightsscrapper.scrapers.services

import com.flightsscrapper.configuration.ApplicationProperties
import com.flightsscrapper.models.{Comment, SourceModel}
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.model.{Element, ElementQuery}

class ScrapingService(appProperties: ApplicationProperties) {

  private var browser = JsoupBrowser()

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
      case None => None
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
    val firstSearchResult = browser
      .get(appProperties.skytraxSite + sourceModel.fullName) >?> element(appProperties.firstResSelPath)
    firstSearchResult match {
      case Some(element) =>
        Some(element.attr("href"))
      case None =>
        println("No results found for airport: " + sourceModel.fullName + "[" + sourceModel.code + "]")
        None
    }
  }

  /**
   * Scrapes model site
   * todo parse date
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
        None
    }

    comments
      .map(comment => {
        val rating = (comment >> allText("div.rating-10 > span:nth-child(1)")).toInt
        val date = comment >> allText("div.body > h3.text_sub_header > time")
        val commentStr = comment >> allText("div.tc_mobile > div.text_content")
        Comment(sourceModel.fullName, sourceModel.code, rating, date, commentStr)
      })
      .toList
  }
}
