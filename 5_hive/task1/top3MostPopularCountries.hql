-- Script for calculation Top 3 most popular countries where booking is successful;
-- Firstable we select hotel_country and count of that country with contition: is_booking=1;
-- Than we group by hotel_country and sort by rate in descending order;
-- Finally output only 3 first records;

SELECT hotel_country, count(hotel_country) AS rate 
FROM train 
WHERE is_booking=1 
GROUP BY hotel_country 
ORDER BY rate DESC 
LIMIT 3;