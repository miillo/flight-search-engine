package com.tass.flight.planner.api

import com.google.inject.Singleton
import com.tass.flight.planner.domain.EnrichedFlight
import com.tass.flight.planner.infrastructure.FlightDataProvider
import com.tass.flight.planner.infrastructure.GraphUtils.findNode
import scalax.collection.Graph
import scalax.collection.edge.WLDiEdge

@Singleton
class JourneyPlanner() {
  private lazy val flightsGraph: Graph[String, WLDiEdge] = FlightGraphBuilder.build(FlightDataProvider.enrichedFlights)

  def plan(start: String, destination: String): Option[Seq[EnrichedFlight]] = {
    val startNode = findNode(flightsGraph, start)
    val endNode = findNode(flightsGraph, destination)
    startNode
      .shortestPathTo(endNode)
      .map(path => path.edges.map(_.label.asInstanceOf[EnrichedFlight]).toSeq)
  }
}
