name := "elastic-reader"

version := "1.0"

scalaVersion := "2.11.12"

val sparkVersion = "2.4.0"
val elasticVersion = "6.6.0"
val scalaTestVersion = "3.0.5"

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.11" % sparkVersion,
  "org.apache.spark" % "spark-sql_2.11" % sparkVersion,
  "org.apache.spark" % "spark-sql-kafka-0-10_2.11" % sparkVersion,
  "org.elasticsearch" % "elasticsearch-hadoop" % elasticVersion,
  "org.scalatest" % "scalatest_2.11" % scalaTestVersion,
  "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)