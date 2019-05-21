package com.kozitski.producer.runner;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.springframework.stereotype.Component;

/**
 * The type Option configurer.
 */
@Component
public class OptionConfigurer {

    /**
     * Init all available options.
     *
     * @param options the options
     */
    void initOptions(Options options) {

        // Topic name option
        Option topic = new Option("t", "topic", true, "kafka topic name");
        topic.setRequired(true);
        options.addOption(topic);

        // Threads number option
        Option numberOfThreads = new Option("threads", "threads", true, "the number of threads(1 by default)");
        numberOfThreads.setRequired(false);
        options.addOption(numberOfThreads);

        // Delay time option
        Option delay = new Option("d", "delay", true, "time in millis to configure delay between events in each thread(100 by default)");
        delay.setRequired(false);
        options.addOption(delay);

        // Host name option
        Option hostName = new Option("h", "host", true, "host name");
        hostName.setRequired(true);
        options.addOption(hostName);

    }

}
