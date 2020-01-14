package com.flightsscrapper.models

import org.mongodb.scala.bson.ObjectId

object Comment {
  def apply(rate: Int, date: String, comment: String): Comment = Comment(new ObjectId(), rate, date, comment)
}

case class Comment(_id: ObjectId, rate: Int, date: String, comment: String)
