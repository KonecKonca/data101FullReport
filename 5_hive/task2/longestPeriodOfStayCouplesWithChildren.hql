-- script to calculate the longest period of stay of couples with children;
-- For transformating date from string to date type we use to_date() function;
-- And calculate date differance with help of function datediff();
-- After that we search all records number of parrnts is 2 and number of hildren more than 1;
-- Finnaly we ourpul only biggest record;

SELECT max(datediff(to_date(srch_co), to_date(srch_ci))) AS time_period FROM test 
WHERE srch_adults_cnt=2 AND srch_children_cnt>=1;