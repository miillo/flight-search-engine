package com.tass.flight.planner.infrastructure

import scalax.collection.Graph
import scalax.collection.edge.WLDiEdge

object GraphUtils {
  def findNode(graph: Graph[String, WLDiEdge], node: String): graph.NodeT = {
    graph.find(node).getOrElse(throw new RuntimeException("Cannot find node. It should never happen."))
  }
}
