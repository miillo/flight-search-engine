package com.tass.flightsscrapper.supervisors.services

import com.tass.common.FileUtils
import com.tass.flightsscrapper.models.SourceModel

object FileReader {
  def readAirportsFile(filePath: String): Seq[SourceModel] = {
    FileUtils
      .loadFromResources(filePath)
      .tail
      .map(line => {
          val splittedLine = line.split("\t")
        SourceModel(splittedLine(3).trim, splittedLine(0).trim)
      })
  }

  def readAirlinesFile(filePath: String): Seq[SourceModel] = {
    FileUtils
      .loadFromResources(filePath)
      .tail
      .map(line => {
        val splittedLine = line.split("\t")
        SourceModel(splittedLine(1).trim, splittedLine(0).trim)
      })
      .toList
  }
}
