package com.kozitski.producer.util;

import com.kozitski.producer.util.generator.RandomGenerator;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class RandomGeneratorTest {
    private static RandomGenerator randomGenerator;

    @BeforeClass
    public static void init(){
        randomGenerator = new RandomGenerator();
    }

    @Test
    @UseDataProvider("rangesDataProvider")
    public void intGenerateTest(int minBound, int maxBound){
        int result = randomGenerator.generateRandomInt(minBound, maxBound);
        boolean actual = true;

        Assert.assertEquals(actual, result >= minBound && result <= maxBound);
    }

    @Test
    @UseDataProvider("rangesDataProvider")
    public void longGenerateTest(int minBound, int maxBound){
        long result = randomGenerator.generateRandomLong(minBound, maxBound);
        boolean actual = true;

        Assert.assertEquals(actual, result >= minBound && result <= maxBound);
    }

    @Test
    @UseDataProvider("rangesDataProvider")
    public void doubleGenerateTest(double minBound, double maxBound){
        double result = randomGenerator.generateRandomDouble(minBound, maxBound);
        boolean actual = true;

        Assert.assertEquals(actual, result >= minBound && result <= maxBound);
    }

    @DataProvider
    public static Object[][] rangesDataProvider() {
        return new Object[][] {
                {0, 10},
                {5, 9},
                {Integer.MIN_VALUE, Integer.MAX_VALUE},
                {-2434354, -3435}
        };
    }

}
