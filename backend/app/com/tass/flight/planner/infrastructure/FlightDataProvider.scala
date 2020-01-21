package com.tass.flight.planner.infrastructure

import com.tass.common.FileUtils
import com.tass.flight.planner.domain._

object FlightDataProvider {

  lazy val airlines: Seq[Airline] = FileUtils
    .loadFromResources("airlines.dat")
    .tail
    .map(_.split("\t").toSeq)
    .map(line => Airline(line.head, line(1)))

  lazy val flights: Seq[Flight] = FileUtils
    .loadFromResources("routes.dat")
    .drop(2)
    .map(_.split("\t").toSeq)
    .map(line => Flight(line.head, line(1), line(2)))

  private lazy val airportsMap: Map[String, Airport] = FileUtils
    .loadFromResources("airports.dat")
    .tail
    .map(_.split("\t").toSeq)
    .map(line => line.head.trim -> Airport(line.head.trim, line(1).toFloat, line(2).toFloat, line(3).trim))
    .toMap

  lazy val airports: Seq[Airport] = flights
    .flatMap(f => Seq(f.from, f.to))
    .groupBy(s => s)
    .view
    .mapValues(_.length)
    .toSeq
    .sortBy { case (_, occurrences) => occurrences }
    .map { case (airport, _) => airport }
    .takeRight(300)
    .map(airport => airportsMap(airport))
    .sortBy(_.name)

  lazy val enrichedFlights: Seq[EnrichedFlight] = FlightEnricher.join(flights, airlines, airports)
}
