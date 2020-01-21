import sbt._

object Dependencies {

  object versions {
    val akkaActor = "2.6.1"
    val typesafeConfig = "1.3.4"
    val scalaScraper = "2.2.0"
    val mongoDb = "2.8.0"
    val stanfordNLPCore = "3.9.2"
    val stanfordNLPParser = "3.9.2"
    val scalaGraph = "1.13.2"
    val playFramework = "2.8.0"
  }

  private val akkaActor: ModuleID = "com.typesafe.akka" %% "akka-actor" % versions.akkaActor
  private val typesafeConfig: ModuleID = "com.typesafe" % "config" % versions.typesafeConfig
  private val scalaScraper: ModuleID = "net.ruippeixotog" %% "scala-scraper" % versions.scalaScraper
  private val mongoDbDriver: ModuleID = "org.mongodb.scala" %% "mongo-scala-driver" % versions.mongoDb
  private val stanfordNLPCore: ModuleID = "edu.stanford.nlp" % "stanford-corenlp" % versions.stanfordNLPCore
  private val stanfordNLPParser: ModuleID = "edu.stanford.nlp" % "stanford-parser" % versions.stanfordNLPParser
  private val stanModels: ModuleID = "edu.stanford.nlp" % "stanford-corenlp" % "3.9.2" classifier "models"
  private val stanModelsEng: ModuleID = "edu.stanford.nlp" % "stanford-corenlp" % "3.9.2" classifier "models-english"
  private val scalaGraph = "org.scala-graph" %% "graph-core" % versions.scalaGraph
  private val playFramework = "com.typesafe.play" %% "play" % versions.playFramework

  val commonDependencies: Seq[ModuleID] = Seq(scalaScraper)
  val scrapperDependencies: Seq[ModuleID] = Seq(akkaActor, typesafeConfig, scalaScraper, mongoDbDriver, stanfordNLPCore, stanfordNLPParser, stanModels, stanModelsEng)
  val backendDependencies: Seq[ModuleID] = Seq(scalaGraph, playFramework)
}
