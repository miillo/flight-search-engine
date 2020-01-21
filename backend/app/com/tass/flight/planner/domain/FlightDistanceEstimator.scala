package com.tass.flight.planner.domain

object FlightDistanceEstimator {
  private val AVERAGE_RADIUS_OF_EARTH_KM = 6371

  def estimate(from: Airport, to: Airport): Double = {
    val latDistance = Math.toRadians(from.lat - to.lat)
    val lngDistance = Math.toRadians(from.long - to.long)
    val sinLat = Math.sin(latDistance / 2)
    val sinLng = Math.sin(lngDistance / 2)
    val a = sinLat * sinLat +
      (Math.cos(Math.toRadians(from.lat)) *
        Math.cos(Math.toRadians(to.lat)) *
        sinLng * sinLng)
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
    AVERAGE_RADIUS_OF_EARTH_KM * c
  }
}
