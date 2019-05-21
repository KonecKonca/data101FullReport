package com.kozitski.elastic.runner

import com.kozitski.elastic.schema.SchemaGenerator
import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.sql.SparkSession

/*
  Receive 3 parameters:
    first: kafkaTopic name
    second: output path
    third: host name
    4-th: server name
 */
class Runner extends App with LazyLogging{

  val spark = SparkSession.builder().appName(Runner.APP_NAME).getOrCreate()
  val topic = args(Runner.TOPIC_ARG_POSITION)
  val path = args(Runner.PATH_ARG_POSITION)
  val host = args(Runner.HOST_ARG_POSITION)
  val kafkaServer = args(Runner.KAFKA_SERVER_ARG_POSITION)

  execute(spark, topic, path)

  /*
    Running elastic read-write application
   */
  def execute(spark: SparkSession, topic: String, path: String): Unit = {

    if(topic == null) {
      logger.error("Topic must be defined")
      System.exit(Runner.FAILED_EXIT_STATUS)
    }

    if(path == null) {
      logger.error("Output path is necessary to define")
      System.exit(Runner.FAILED_EXIT_STATUS)
    }

    if(host == null) {
      logger.error("Host is necessary to define")
      System.exit(Runner.FAILED_EXIT_STATUS)
    }

    if(kafkaServer == null) {
      logger.error("Kafka server is necessary to define")
      System.exit(Runner.FAILED_EXIT_STATUS)
    }

    val elasticReadWriter = new ElasticReadWriter()
    val dataFrame = elasticReadWriter.readStream(spark, topic, kafkaServer).transform((new SchemaGenerator).splitIntoColumns)
    elasticReadWriter.writeStream(dataFrame, path, host).awaitTermination()

  }

}
/*
  Runner constants
 */
object Runner{
  val TOPIC_ARG_POSITION = 0
  val PATH_ARG_POSITION = 1
  val HOST_ARG_POSITION = 2
  val KAFKA_SERVER_ARG_POSITION = 3
  val FAILED_EXIT_STATUS = 1
  val APP_NAME = "kafka reader"
}
