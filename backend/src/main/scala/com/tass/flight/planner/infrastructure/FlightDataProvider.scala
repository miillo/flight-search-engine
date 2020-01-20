package com.tass.flight.planner.infrastructure

import com.tass.common.FileUtils
import com.tass.flight.planner.domain._

object FlightDataProvider {
  def provide(): Seq[EnrichedFlight] = {
    val airlines = FileUtils
      .loadFromResources("airlines.dat")
      .tail
      .map(_.split("\t").toSeq)
      .map(line => Airline(line.head, line(1)))

    val airports = FileUtils
      .loadFromResources("airports.dat")
      .tail
      .map(_.split("\t").toSeq)
      .map(line => Airport(line.head.trim, line(1).toFloat, line(2).toFloat, line(3).trim))

    val flights = FileUtils
      .loadFromResources("routes.dat")
      .drop(2)
      .map(_.split("\t").toSeq)
      .map(line => Flight(line.head, line(1), line(2)))

    FlightEnricher.join(flights, airlines, airports)
  }
}
