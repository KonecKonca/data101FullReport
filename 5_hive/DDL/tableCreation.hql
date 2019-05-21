-- Create tables from CSV files;

-- ############################################################################################;
-- Create table for submissions;
CREATE TABLE submissions (id BIGINT, hotel_cluster STRING) 
ROW FORMAT DELIMITED FIELDS 
TERMINATED BY ',' 
STORED AS TEXTFILE TBLPROPERTIES("skip.header.line.count"="1");

-- Load data from hdfs without copy of sources;
LOAD DATA INPATH '/user/maria_dev/data/sample_submission.csv' OVERWRITE INTO TABLE submissions;
-- ############################################################################################;



-- ############################################################################################;
-- Create table for test;
CREATE TABLE test (
	id BIGINT, date_time STRING, site_name INT, posa_continent INT, user_location_country INT, 
	user_location_region INT, user_location_city INT, orig_destination_distance DOUBLE, 
	user_id INT, is_mobile INT, is_package INT, channel INT, srch_ci STRING, srch_co STRING, 
	srch_adults_cnt INT, srch_children_cnt INT, srch_rm_cnt STRING, srch_destination_id INT, 
	srch_destination_type_id INT, hotel_continent INT, hotel_country INT, hotel_market INT)  
ROW FORMAT DELIMITED FIELDS 
TERMINATED BY ',' 
STORED AS TEXTFILE TBLPROPERTIES("skip.header.line.count"="1");

-- Load data from hdfs without copy of sources;
LOAD DATA INPATH '/user/maria_dev/data/test.csv' OVERWRITE INTO TABLE test;
-- ############################################################################################;



-- ############################################################################################;
-- Create table for train;
CREATE TABLE train (date_time STRING, site_name INT, posa_continent INT, user_location_country INT, 
	user_location_region INT, user_location_city INT, orig_destination_distance DOUBLE, 
	user_id INT, is_mobile INT, is_package INT, channel INT, srch_ci STRING, srch_co STRING, 
	srch_adults_cnt INT, srch_children_cnt INT, srch_rm_cnt STRING, srch_destination_id INT, 
	srch_destination_type_id INT, is_booking INT, cnt BIGINT, hotel_continent INT, 
	hotel_country INT, hotel_market INT, hotel_cluster INT)  
ROW FORMAT DELIMITED FIELDS 
TERMINATED BY ',' 
STORED AS TEXTFILE TBLPROPERTIES("skip.header.line.count"="1");

-- Load data from hdfs without copy of sources;
LOAD DATA INPATH '/user/maria_dev/data/train.csv' OVERWRITE INTO TABLE train;
-- ############################################################################################;