import sbt._

object Dependencies {
  object versions {
    val akkaActor = "2.6.1"
    val typesafeConfig = "1.3.4"
    val scalaScraper = "2.2.0"
    val scala = "2.13.1"
  }

  private val akkaActor: ModuleID = "com.typesafe.akka" %% "akka-actor" % versions.akkaActor
  private val typesafeConfig: ModuleID = "com.typesafe" % "config" % versions.typesafeConfig
  private val scalaScraper: ModuleID = "net.ruippeixotog" %% "scala-scraper" % versions.scalaScraper

  val dependencies: Seq[ModuleID] = Seq(akkaActor, typesafeConfig, scalaScraper)
}
