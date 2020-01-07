package com.flightsscrapper

import com.flightsscrapper.configuration.ApplicationProperties

object FlightsScrapper {

  def main(args: Array[String]): Unit = {
    val appProperties = new ApplicationProperties("scrapper/src/resources/application.conf")
    print(appProperties.sourceDataPath)
  }
}
