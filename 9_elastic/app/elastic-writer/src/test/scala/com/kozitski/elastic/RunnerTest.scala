package com.kozitski.elastic

import com.kozitski.elastic.schema.SchemaGenerator
import org.apache.spark.sql.execution.streaming.MemoryStream
import org.apache.spark.sql.types._
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.scalatest.{BeforeAndAfterAll, FunSuite}

class RunnerTest extends FunSuite with BeforeAndAfterAll{
  
  var spark: SparkSession = _

  override def beforeAll() {
    spark = SparkSession
        .builder()
          .appName("kafka reader test")
          .master("local[*]")
        .getOrCreate()
  }

  override def afterAll(): Unit = {
    spark.stop()
  }

  test("number of columns test") {

    val df = mockStreamGenerate()
    val transformedDF = (new SchemaGenerator).splitIntoColumns(df)
    assertResult(transformedDF.columns.length)(24)

  }

  test("schema compare test") {

    val expectedSchema = new StructType(Array(
      StructField("date_time", TimestampType, nullable = true),
      StructField("site_name", IntegerType, nullable = true),
      StructField("posa_continent", IntegerType, nullable = true),
      StructField("user_location_country", IntegerType, nullable = true),
      StructField("user_location_region", IntegerType, nullable = true),
      StructField("user_location_city", IntegerType, nullable = true),
      StructField("orig_destination_distance", DoubleType, nullable = true),
      StructField("user_id", IntegerType, nullable = true),
      StructField("is_mobile", IntegerType, nullable = true),
      StructField("is_package", IntegerType, nullable = true),
      StructField("channel", IntegerType, nullable = true),
      StructField("srch_ci", DateType, nullable = true),
      StructField("srch_co", DateType, nullable = true),
      StructField("srch_adults_cnt", IntegerType, nullable = true),
      StructField("srch_children_cnt", IntegerType, nullable = true),
      StructField("srch_rm_cnt", IntegerType, nullable = true),
      StructField("srch_destination_id", IntegerType, nullable = true),
      StructField("srch_destination_type_id", IntegerType, nullable = true),
      StructField("is_booking", IntegerType, nullable = true),
      StructField("cnt", LongType, nullable = true),
      StructField("hotel_continent", IntegerType, nullable = true),
      StructField("hotel_country", IntegerType, nullable = true),
      StructField("hotel_market", IntegerType, nullable = true),
      StructField("hotel_cluster", IntegerType, nullable = true)
    ))

    val df = mockStreamGenerate()

    val transformedDF = (new SchemaGenerator).splitIntoColumns(df)

    val actualSchema = transformedDF.schema

    assertResult(expectedSchema)(actualSchema)

  }

  def mockStreamGenerate() : DataFrame = {

    val localSpark = spark

    import localSpark.implicits._

    val memoryStream = new MemoryStream[String](42, spark.sqlContext)

    memoryStream.addData(Seq(
      "1991-08-12 07:46:59,2,3,12,348,48862,2234.2641,12,0,1,9,1991-08-27,1991-08-31,2,0,1,822,1,0,3,2,2,628,1",
      "2004-07-16 10:00:06,2,3,12,189,10067,,21,0,0,2,1991-08-01,1991-08-02,2,0,1,8267,1,0,1,2,2,675,98",
      "1991-11-22 20:55:38,30,4,195,991,47725,,1048,1,0,9,2015-06-26,2015-06-28,2,0,1,8803,1,0,1,3,151,1197,5"
    ))

    memoryStream.toDF()

  }

}
