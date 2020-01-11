package com.flightsscrapper.supervisors.services

import com.flightsscrapper.models.{Airline, Airport}

import scala.io.Source

object FileReader {

  def readAirportsFile(filePath: String): List[Airport] = {
    val source = Source.fromFile(filePath, "ISO-8859-1")
    val airports = source
      .getLines()
      .drop(1)
      .filter(line => if (!line.isBlank) true else false)
      .map(line => {
          val splittedLine = line.split("\t")
          Airport(splittedLine(0).trim, splittedLine(3).trim)
      })
      .toList

    source.close()
    airports
  }

  def readAirlinesFile(filePath: String): List[Airline] = {
    val source = Source.fromFile(filePath, "ISO-8859-1")
    val airports = source
      .getLines()
      .drop(1)
      .filter(line => if (!line.isBlank) true else false)
      .map(line => {
        val splittedLine = line.split("\\s+")
        Airline(splittedLine(0).trim, splittedLine(1).trim)
      })
      .toList

    source.close()
    airports
  }
}
