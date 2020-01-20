package com.tass.flight.planner.application

import com.tass.flight.planner.domain.EnrichedFlight
import scalax.collection.Graph
import scalax.collection.edge.Implicits._
import scalax.collection.edge.WLDiEdge

object FlightGraphBuilder {
  def build(enrichedFlights: Seq[EnrichedFlight]): Graph[String, WLDiEdge] = {
    Graph(
      enrichedFlights
        .map(flight => (flight.from.id ~%+> flight.to.id)(flight.distance, flight)): _*
    )
  }
}
