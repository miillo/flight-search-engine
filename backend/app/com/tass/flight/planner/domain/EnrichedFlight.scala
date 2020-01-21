package com.tass.flight.planner.domain

case class EnrichedFlight(flightId: Int, airline: String, from: Airport, to: Airport, distance: Double)
