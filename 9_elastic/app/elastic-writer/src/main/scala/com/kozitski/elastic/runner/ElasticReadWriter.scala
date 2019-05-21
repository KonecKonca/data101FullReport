package com.kozitski.elastic.runner

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.streaming.StreamingQuery

class ElasticReadWriter {

  /**
    * Reads stream from the specified {{{topic}}}
    * @param spark is a [[SparkSession]] object.
    * @param topic is the name of kafka topic.
    * @return [[DataFrame]] object.
    */
  def readStream(spark: SparkSession, topic: String, kafkaServer: String): DataFrame = {

    spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", kafkaServer)
      .option("subscribe", topic)
      .load()

  }

  /**
    * Writes stream into elasticsearch.
    * @param df is a [[DataFrame]] object.
    * @param path is a HDFS path where to store checkpoints.
    * @return [[StreamingQuery]] object.
    */
  def writeStream(df:DataFrame, path: String, host: String): StreamingQuery = {

    df.writeStream
      .outputMode("append")
      .queryName("kafka_spark-streaming_elastic_writer")
      .format("org.elasticsearch.spark.sql")
      .option("es.nodes", host)
      .option("es.index.auto.create", "true")
      .option("es.resource", "index/type")
      .option("checkpointLocation", path + "/checkpoints")
      .start("/events/train")

  }

}
