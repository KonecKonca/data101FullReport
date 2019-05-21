package com.kozitski.producer.runner;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The type Producer application runner.
 */
@Component
@Slf4j
public class ProducerApplicationRunner {
    private static final String TOPIC = "topic";
    private static final String THREADS = "threads";
    private static final String DELAY = "delay";
    private static final String HOST_NAME = "host";

    private static final int DEFAULT_NUM_OF_THREADS = 1;
    private static final int DEFAULT_DELAY = 100;

    private PropertiesGenerator propertiesGenerator = new PropertiesGenerator();

    private OptionConfigurer optionConfigurer = new OptionConfigurer();

    /**
     * Sets properties generator.
     *
     * @param propertiesGenerator the properties generator
     */
    @Autowired
    public void setPropertiesGenerator(PropertiesGenerator propertiesGenerator) {
        this.propertiesGenerator = propertiesGenerator;
    }

    /**
     * Sets option configurer.
     *
     * @param optionConfigurer the option configurer
     */
    @Autowired
    public void setOptionConfigurer(OptionConfigurer optionConfigurer) {
        this.optionConfigurer = optionConfigurer;
    }

    /**
     * Start.
     *
     * @param args the args
     */
    public void start(String ... args) {
        Options options = new Options();
        optionConfigurer.initOptions(options);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        }
        catch (ParseException e) {
            log.error("Incorrect argument", e);
            System.exit(NumberUtils.INTEGER_ZERO);
        }

        String topic = cmd.getOptionValue(TOPIC);
        String numberOfThreadsStr = cmd.getOptionValue(THREADS);
        String delayStr = cmd.getOptionValue(DELAY);
        String hostName = cmd.getOptionValue(HOST_NAME);

        int numberOfThreads;
        if (numberOfThreadsStr == null) {
            numberOfThreads = DEFAULT_NUM_OF_THREADS;
        }
        else {
            numberOfThreads = Integer.parseInt(numberOfThreadsStr);
            if (numberOfThreads < NumberUtils.INTEGER_ONE) {
                throw new IllegalArgumentException("The number of threads must be positive");
            }
        }

        int delay;
        if (delayStr == null) {
            delay = DEFAULT_DELAY;
        }
        else {
            delay = Integer.parseInt(delayStr);
            if (delay < NumberUtils.INTEGER_ONE) {
                throw new IllegalArgumentException("Delay must be positive");
            }
        }

        for (int i = 0; i < numberOfThreads; i++) {
            new Thread(new ProducerThread(topic, propertiesGenerator.generateProperties(hostName), delay)).start();
        }

    }

}
