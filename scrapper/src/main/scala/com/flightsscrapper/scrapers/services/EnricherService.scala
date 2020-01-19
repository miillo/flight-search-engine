package com.flightsscrapper.scrapers.services

import java.util.Properties

import com.flightsscrapper.configuration.ApplicationProperties
import com.flightsscrapper.models.Comment
import com.flightsscrapper.persistence.services.MongoDbService
import edu.stanford.nlp.ling.CoreAnnotations
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations
import edu.stanford.nlp.pipeline.{Annotation, StanfordCoreNLP}
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations

class EnricherService {

//  def enrich(comments: List[Comment]): Unit = {
//    for (comment <- comments) {
//      println(getStanfordSentimentRate(comment.comment))
//    }
//  }

   def getStanfordSentimentRate(comment: String): Int = {
    val props = new Properties()
    props.setProperty("annotators", "tokenize, ssplit, parse, sentiment")
    val pipeline = new StanfordCoreNLP(props)
    var totalRate = 0
    val linesArr = comment.split("\\.")

    for (i <- 0 until linesArr.length) {
      if (linesArr(i) != null) {
        val annotation: Annotation = pipeline.process(linesArr(i))
        val coreMapList = annotation.get(classOf[CoreAnnotations.SentencesAnnotation])

        for (i <- 0 until coreMapList.size()) {
          val tree = coreMapList.get(i).get(classOf[SentimentCoreAnnotations.SentimentAnnotatedTree])
          val score = RNNCoreAnnotations.getPredictedClass(tree)
          totalRate = totalRate + (score - 2)
        }
      }
    }
    totalRate
  }

}
