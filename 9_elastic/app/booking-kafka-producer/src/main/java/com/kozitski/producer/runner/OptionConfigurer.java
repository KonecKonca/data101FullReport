package com.kozitski.producer.runner;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.springframework.stereotype.Component;

@Component
public class OptionConfigurer {

    void initOptions(Options options) {

        Option topic = new Option("t", "topic", true, "kafka topic name");
        topic.setRequired(true);
        options.addOption(topic);

        Option numberOfThreads = new Option("threads", "threads", true, "the number of threads(1 by default)");
        numberOfThreads.setRequired(false);
        options.addOption(numberOfThreads);

        Option delay = new Option("d", "delay", true, "time in millis to configure delay between events in each thread(100 by default)");
        delay.setRequired(false);
        options.addOption(delay);

        Option hostName = new Option("h", "host", true, "host name");
        hostName.setRequired(true);
        options.addOption(hostName);

    }

}
