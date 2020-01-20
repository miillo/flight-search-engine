package com.tass.common

import java.nio.charset.Charset
import java.nio.file.{Files, Paths, StandardOpenOption}

import scala.io.{Codec, Source}

object FileUtils {
  implicit val codec = Codec("ISO-8859-1")

  def loadFromResources(filePath: String): Seq[String] = {
    val source = Source.fromResource(filePath)
    val lines = source
      .getLines()
      .filter(line => if (!line.isEmpty) true else false)
      .toSeq

    source.close()
    lines
  }

  def saveAggregateScore(filePath: String, aggScore: Int, fullName: String): Unit = {
    val path = Paths.get(filePath)
    val bufWriter = Files.newBufferedWriter(path, Charset.forName("ISO-8859-1"), StandardOpenOption.CREATE, StandardOpenOption.APPEND)
    val textToWrite = fullName + "\t" + aggScore
    bufWriter.write(textToWrite)
    bufWriter.newLine()
    bufWriter.close()
  }
}
