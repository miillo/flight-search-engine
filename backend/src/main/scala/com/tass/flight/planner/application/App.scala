package com.tass.flight.planner.application

import com.tass.flight.planner.infrastructure.FlightDataProvider

object App {
  def main(args: Array[String]): Unit = {
    val enrichedFlights = FlightDataProvider.provide()
    val flightGraph = FlightGraphBuilder.build(enrichedFlights)
    val journeyPlanner = new JourneyPlanner(flightGraph)

    println(journeyPlanner.plan("WAW","YQY"))
  }
}
