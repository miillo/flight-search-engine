package com.tass.flight.planner.domain

object FlightEnricher {
  def join(routes: Seq[Flight], airlines: Seq[Airline], airports: Seq[Airport]): Seq[EnrichedFlight] = {
    val airlinesMap = airlines.map(airline => airline.id -> airline).toMap
    val airportsMap = airports.map(airport => airport.id -> airport).toMap

    routes
      .zipWithIndex
      .filter { case (flight, _) => airportsMap.contains(flight.to) && airportsMap.contains(flight.from)}
      .map { case (flight, flightId) =>
        val start = airportsMap(flight.from)
        val destination = airportsMap(flight.to)
        EnrichedFlight(
          flightId,
          airlinesMap(flight.airlineId).name,
          start,
          destination,
          FlightDistanceEstimator.estimate(start, destination)
        )
      }
  }
}
