package com.tass.flightsscrapper.persistence.services

import com.tass.flightsscrapper.configuration.ApplicationProperties
import com.tass.flightsscrapper.models.Comment
import com.tass.flightsscrapper.persistence.services.Helpers._
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.{MongoCollection, _}

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.language.postfixOps

class MongoDbService(appProperties: ApplicationProperties) {
  val codecRegistry: CodecRegistry = fromRegistries(fromProviders(classOf[Comment]), DEFAULT_CODEC_REGISTRY)
  val mongoClient: MongoClient = MongoClient(appProperties.mongoDbConnStr)
  val database: MongoDatabase = mongoClient.getDatabase(appProperties.mongoDbName).withCodecRegistry(codecRegistry)
  val collection: MongoCollection[Comment] = database.getCollection(appProperties.mongoDbCollection)

  def saveInstances(comments: Seq[Comment]): Unit = collection.insertMany(comments).printResults()

  def getElements(name: String): Seq[Comment] = {
    val res = collection.find(equal("name", "Cayman Airways"))
    Await.result(res.toFuture(), Duration.Inf)
  }

}
