package com.monolitospizza.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Matt Stine
 */
@Configuration
@EnableRabbit
@Profile("store")
public class StoreAmqpConfig {

    @Value("${monolitos.storeId}")
    private String storeId;

    @Bean
    public String incomingOrderQueueName() {
        return "monolitos.store." + storeId + ".incomingOrders";
    }

    @Bean
    public Queue incomingOrderQueue() {
        return new Queue(incomingOrderQueueName());
    }
}
