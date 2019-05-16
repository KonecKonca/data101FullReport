package com.kozitski.spark.runner

import com.kozitski.spark.schema.SchemaGenerator
import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.sql.SparkSession

/*
  Receive 3 parameters:
    first: kafkaTopic name
    second: output path
    third: server name
 */
class Runner extends App with LazyLogging{

  val spark = SparkSession.builder().appName(Runner.APP_NAME).getOrCreate()
  val topic = args(Runner.TOPIC_ARG_POSITION)
  val path = args(Runner.PATH_ARG_POSITION)
  val server = args(Runner.SERVER_NAME_ARG_POSITION)

  start(spark, topic, path, server)

  /*
    Running elastic read-write application
   */
  def start(spark: SparkSession, topic: String, path: String, server: String): Unit = {

    if(topic == null) {
      logger.error("Topic must be defined")
      System.exit(Runner.FAILED_EXIT_STATUS)
    }

    if(path == null) {
      logger.error("Output path is necessary to define")
      System.exit(Runner.FAILED_EXIT_STATUS)
    }

    if(server == null) {
      logger.error("Server must be defined")
      System.exit(Runner.FAILED_EXIT_STATUS)
    }

    val elasticReadWriter = new KafkaStreamingReadWriter()
    val dataFrame = elasticReadWriter.readStream(spark, topic, server).transform((new SchemaGenerator).splitIntoColumns)
    elasticReadWriter.writeStream(dataFrame, path).awaitTermination()

  }

}

/*
  Runner constants
 */
object Runner{
  val TOPIC_ARG_POSITION = 0
  val PATH_ARG_POSITION = 1
  val SERVER_NAME_ARG_POSITION = 2
  val FAILED_EXIT_STATUS = 1
  val APP_NAME = "kafka reader"
}
