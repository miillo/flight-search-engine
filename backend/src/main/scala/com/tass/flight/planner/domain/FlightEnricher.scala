package com.tass.flight.planner.domain

object FlightEnricher {
  def join(routes: Seq[Flight], airlines: Seq[Airline], airports: Seq[Airport]): Seq[EnrichedFlight] = {
    val airlinesMap = airlines.map(airline => airline.id -> airline).toMap
    val airportsMap = airports.map(airport => airport.id -> airport).toMap

    routes
      .zipWithIndex
      .map { case (flight, flightId) =>
        val start = airportsMap(flight.from)
        val destination = airportsMap(flight.to)
        EnrichedFlight(
          flightId,
          flight,
          start,
          destination,
          airlinesMap(flight.airlineId),
          FlightDistanceEstimator.estimate(start, destination)
        )
      }
  }
}
