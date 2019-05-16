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
  def readStream(spark: SparkSession, topic: String): DataFrame = {

    spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "sandbox-hdp.hortonworks.com:6667")
      .option("subscribe", topic)
      .load()

  }

  /**
    * Writes stream into elasticsearch.
    * @param df is a [[DataFrame]] object.
    * @param path is a HDFS path where to store checkpoints.
    * @return [[StreamingQuery]] object.
    */
  def writeStream(df:DataFrame, path: String): StreamingQuery = {

    df.writeStream
      .outputMode("append")
      .queryName("kafka_spark-streaming_elastic_writer")
      .format("org.elasticsearch.spark.sql")
      .option("es.port", "9200")
      .option("es.nodes", "localhost:9200")
      .option("es.index.auto.create", "true")
      .option("es.resource", "index/type")
      .option("checkpointLocation", path + "/checkpoints")
      .start("/events/train")

  }

}
