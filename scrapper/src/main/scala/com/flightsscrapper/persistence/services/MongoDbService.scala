package com.flightsscrapper.persistence.services

import com.flightsscrapper.configuration.ApplicationProperties
import org.mongodb.scala._

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps

class MongoDbService(appProperties: ApplicationProperties) {

  val mongoClient: MongoClient = MongoClient(appProperties.mongoDbConnStr)
  val database: MongoDatabase = mongoClient.getDatabase("local")

  val collection: MongoCollection[Document] = database.getCollection(appProperties.mongoDbAirportColl)
  val ex: Future[Seq[Document]] = collection.find().collect().head()
  Await.result(ex, atMost = 5000 millis)
  println(ex)
}
