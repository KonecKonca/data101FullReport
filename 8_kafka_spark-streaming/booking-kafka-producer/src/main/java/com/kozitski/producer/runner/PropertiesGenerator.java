package com.kozitski.producer.runner;

import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * The type Properties generator.
 */
@Component
public class PropertiesGenerator {
    private static final String BOOTSTRAP_SERVERS = "bootstrap.servers";
    private static final String ACKS = "acks";
    private static final String ALL = "all";
    private static final String DELIVERY_TIMEOUT_MS = "delivery.timeout.ms";
    private static final int DEVAULT_TIMEOUT = 30000;
    private static final String BATCH_SIZE = "batch.size";
    private static final int DEFAULT_BATCH_SIZE = 16384;
    private static final String BUFFER_MEMORY = "buffer.memory";
    private static final int DEFAULT_BUFFER_MEMORY = 33554432;
    private static final String KEY_SERIALIZER = "key.serializer";
    private static final String KEY_KAFKA_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";
    private static final String VALUE_SERIALIZER = "com.kozitski.producer.util.BookingEventSerializer";

    /**
     * Generate properties properties.
     *
     * @param server the server
     * @return the properties
     */
    public Properties generateProperties(String server){
        Properties props = new Properties();

        props.put(BOOTSTRAP_SERVERS, server);
        props.put(ACKS, ALL);
        props.put(DELIVERY_TIMEOUT_MS, DEVAULT_TIMEOUT);
        props.put(BATCH_SIZE, DEFAULT_BATCH_SIZE);
        props.put(BUFFER_MEMORY, DEFAULT_BUFFER_MEMORY);
        props.put(KEY_SERIALIZER, KEY_KAFKA_SERIALIZER);
        props.put(VALUE_SERIALIZER, VALUE_SERIALIZER);

        return props;
    }

}
