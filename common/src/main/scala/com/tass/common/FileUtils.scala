package com.tass.common

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
}
