-- Script to calculate top 3 most popular hotels were not booked;
-- Firstable we select all attributes which identify hotel and rate of hotel;
-- Than we group by hotel unique identifier and order by rate with descending;
-- Finally output only 3 first records;

SELECT hotel_continent, hotel_country, hotel_market, count(*) rate FROM train 
WHERE is_booking=0 
GROUP BY hotel_continent, hotel_country, hotel_market 
ORDER BY rate DESC 
LIMIT 3;