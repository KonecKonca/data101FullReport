package com.kozitski.spark.runner

import org.apache.spark.sql.streaming.StreamingQuery
import org.apache.spark.sql.{DataFrame, SparkSession}

class KafkaStreamingReadWriter {

  /**
    * Reads stream from the specified {{{topic}}}
    * @param spark is a [[SparkSession]] object.
    * @param topic is the name of kafka topic.
    * @return [[DataFrame]] object.
    */
  def readStream(spark: SparkSession, topic: String, server: String): DataFrame = {

    spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", server)
      .option("subscribe", topic)
      .load()

  }

  /**
    * Writes stream into specified {{{path}}}.
    * @param df is a [[DataFrame]] object.
    * @param path is a HDFS path where to store output.
    * @return [[StreamingQuery]] object.
    */
  def writeStream(df:DataFrame, path: String): StreamingQuery = {

    df.writeStream
      .format("csv")
      .option("path", path)
      .option("checkpointLocation", path + "/checkpoints")
      .start()

  }

}
