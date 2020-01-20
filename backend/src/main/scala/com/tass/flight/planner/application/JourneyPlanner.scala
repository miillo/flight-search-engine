package com.tass.flight.planner.application

import com.tass.flight.planner.domain.EnrichedFlight
import com.tass.flight.planner.infrastructure.GraphUtils.findNode
import scalax.collection.Graph
import scalax.collection.edge.WLDiEdge

class JourneyPlanner(flightsGraph: Graph[String, WLDiEdge]) {

  def plan(start: String, destination: String): Option[Seq[EnrichedFlight]] = {
    val startNode = findNode(flightsGraph, start)
    val endNode = findNode(flightsGraph, destination)
    startNode
      .shortestPathTo(endNode)
      .map(path => path.edges.map(_.label.asInstanceOf[EnrichedFlight]).toSeq)
  }
}
