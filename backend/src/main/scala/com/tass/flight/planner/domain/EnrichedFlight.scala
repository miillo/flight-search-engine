package com.tass.flight.planner.domain

case class EnrichedFlight(flightId: Int, flight: Flight, from: Airport, to: Airport, airline: Airline, distance: Double)
