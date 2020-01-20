package com.tass.flightsscrapper.configuration

import com.typesafe.config.{Config, ConfigException, ConfigFactory}

sealed class ApplicationProperties {
  private val config: Config = createConfigFactory()

  val airportsFilePath: String = config.getString("source-data.airportsFile")
  val airlinesFilePath: String = config.getString("source-data.airlinesFile")
  val skytraxSite: String = config.getString("sites.skytrax")
  val siteTimeout: Int = config.getInt("sites.timeout")
  val firstResSelPath: String = config.getString("scraping.first-search-result")
  val commentSection: String = config.getString("scraping.comment-section")
  val httpDelay: Int = config.getInt("scraping.http-delay")
  val noOfScraperActors: Int = config.getInt("actors.no-of-scraper-actors")
  val mongoDbConnStr: String = config.getString("mongo-db.connection-str")
  val mongoDbName: String = config.getString("mongo-db.db-name")
  val mongoDbCollection: String = config.getString("mongo-db.collection")

  private def createConfigFactory(): Config = {
    try {
      val config = ConfigFactory.load()
      if (config.isEmpty) {
        System.err.println("Config creation failure. Reason: Configuration file not found.")
        System.exit(1)
      }
      config
    } catch {
      case ce: ConfigException =>
        System.err.println("Config creation failure. Reason: " + ce.getMessage)
        System.exit(1)
        null
    }
  }
}
