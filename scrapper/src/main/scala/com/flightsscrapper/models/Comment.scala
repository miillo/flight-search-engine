package com.flightsscrapper.models

import org.mongodb.scala.bson.ObjectId

object Comment {
  def apply(name: String, code: String, rate: Int, date: String, comment: String): Comment =
    Comment(new ObjectId(), name, code, rate, date, comment)
}

case class Comment(_id: ObjectId, name: String, code: String, rate: Int, date: String, comment: String)