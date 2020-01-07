package com.flightsscrapper.configuration

import java.io.File
import com.typesafe.config.{Config, ConfigException, ConfigFactory}

sealed class ApplicationProperties(configPath: String) {
  private val config: Config = createConfigFactory()

  val sourceDataPath: String = config.getString("source-data.path")

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
