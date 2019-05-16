package com.kozitski.spark.schema

import org.apache.spark.sql.DataFrame

/*
    DataFrame schema generator
 */
class SchemaGenerator{

  /**
    * Splits into columns according to schema of train.csv file.
    *
    * @param df is a [[DataFrame]] object.
    * @return [[DataFrame]] object.
    */
  def splitIntoColumns(df: DataFrame) : DataFrame = {

    df.selectExpr("CAST(value AS STRING)")
      .selectExpr(
        "cast(split(value, ',')[0] as timestamp) date_time",
        "cast(split(value, ',')[1] as int) site_name",
        "cast(split(value, ',')[2] as int) posa_continent",
        "cast(split(value, ',')[3] as int) user_location_country",
        "cast(split(value, ',')[4] as int) user_location_region",
        "cast(split(value, ',')[5] as int) user_location_city",
        "cast(split(value, ',')[6] as double) orig_destination_distance",
        "cast(split(value, ',')[7] as int) user_id",
        "cast(split(value, ',')[8] as int) is_mobile",
        "cast(split(value, ',')[9] as int) is_package",
        "cast(split(value, ',')[10] as int) channel",
        "cast(split(value, ',')[11] as date) srch_ci",
        "cast(split(value, ',')[12] as date) srch_co",
        "cast(split(value, ',')[13] as int) srch_adults_cnt",
        "cast(split(value, ',')[14] as int) srch_children_cnt",
        "cast(split(value, ',')[15] as int) srch_rm_cnt",
        "cast(split(value, ',')[16] as int) srch_destination_id",
        "cast(split(value, ',')[17] as int) srch_destination_type_id",
        "cast(split(value, ',')[18] as int) is_booking",
        "cast(split(value, ',')[19] as long) cnt",
        "cast(split(value, ',')[20] as int) hotel_continent",
        "cast(split(value, ',')[21] as int) hotel_country",
        "cast(split(value, ',')[22] as int) hotel_market",
        "cast(split(value, ',')[23] as int) hotel_cluster")

  }

}
