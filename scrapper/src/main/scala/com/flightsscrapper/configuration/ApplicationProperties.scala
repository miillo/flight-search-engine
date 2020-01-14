package com.flightsscrapper.configuration

import java.io.File
import com.typesafe.config.{Config, ConfigException, ConfigFactory}

sealed class ApplicationProperties(configPath: String) {
  private val config: Config = createConfigFactory()

  val airportsFilePath: String = config.getString("source-data.airportsFile")
  val airlinesFilePath: String = config.getString("source-data.airlinesFile")
  val skytraxSite: String = config.getString("sites.skytrax")
  val firstResSelPath: String = config.getString("airport-scraping.first-search-result")
  val commentSection: String = config.getString("airport-scraping.comment-section")
  val noOfScraperActors: Int = config.getInt("actors.no-of-scraper-actors")
  val mongoDbConnStr: String = config.getString("mongo-db.connection-str")
  val mongoDbName: String = config.getString("mongo-db.db-name")
  val mongoDbAirportColl: String = config.getString("mongo-db.airport-collection")

  private def createConfigFactory(): Config = {
    try {
      val config = ConfigFactory.parseFile(new File(configPath))
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
