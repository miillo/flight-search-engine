package com.enricher

import java.util.Properties

import com.flightsscrapper.configuration.ApplicationProperties
import com.flightsscrapper.persistence.services.MongoDbService
import edu.stanford.nlp.ling.CoreAnnotations
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations
import edu.stanford.nlp.pipeline.Annotation
import edu.stanford.nlp.pipeline.StanfordCoreNLP
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations
import edu.stanford.nlp.trees.Tree
import edu.stanford.nlp.util.CoreMap

import scala.util.Properties

class Enricher(appProperties: ApplicationProperties) {
  val mongoDbService = new MongoDbService(appProperties)

  def enrich(): Unit = {
    val comments = mongoDbService.getElements("Cayman Airways")
    for (comment <- comments) {
      println(getStanfordSentimentRate(comment.comment))
    }
  }

  private def getStanfordSentimentRate(comment: String): Int = {
    val props = new Properties()
    props.setProperty("annotators", "tokenize, ssplit, parse, sentiment")
    val pipeline = new StanfordCoreNLP(props)
    var totalRate = 0
    val linesArr = comment.split("\\.")

    for (i <- 0 until linesArr.length) {
      if (linesArr(i) != null) {
        val annotation: Annotation = pipeline.process(linesArr(i))
        val coreMapList = annotation.get(classOf[CoreAnnotations.SentencesAnnotation])
        for (sentence <- coreMapList) {
          val tree = sentence.get(classOf[SentimentCoreAnnotations.SentimentAnnotatedTree])
          val score = RNNCoreAnnotations.getPredictedClass(tree)
          totalRate = totalRate + (score - 2)
        }
      }
    }
  totalRate
  }

}
