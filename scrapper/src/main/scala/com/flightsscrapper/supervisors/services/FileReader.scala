package com.flightsscrapper.supervisors.services

import com.flightsscrapper.models.{SourceModel}

import scala.io.Source

object FileReader {

  def readAirportsFile(filePath: String): List[SourceModel] = {
    val source = Source.fromFile(filePath, "ISO-8859-1")
    val airports = source
      .getLines()
      .drop(1)
      .filter(line => if (!line.isBlank) true else false)
      .map(line => {
          val splittedLine = line.split("\t")
        SourceModel(splittedLine(0).trim, splittedLine(3).trim)
      })
      .toList

    source.close()
    airports
  }

  def readAirlinesFile(filePath: String): List[SourceModel] = {
    val source = Source.fromFile(filePath, "ISO-8859-1")
    val airlines = source
      .getLines()
      .drop(1)
      .filter(line => if (!line.isBlank) true else false)
      .map(line => {
        val splittedLine = line.split("\t")
        SourceModel(splittedLine(1).trim, splittedLine(0).trim)
      })
      .toList

    source.close()
    airlines
  }
}
