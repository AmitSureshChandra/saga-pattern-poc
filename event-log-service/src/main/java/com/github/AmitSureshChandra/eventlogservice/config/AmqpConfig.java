package com.github.AmitSureshChandra.eventlogservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

    @Value("${spring.application.name}")
    private String appName;

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("ecommerce-exchange");
    }

    @Bean
    MessageConverter msgConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory(@Value("${spring.rabbitmq.username}") String username, @Value("${spring.rabbitmq.password}") String password, @Value("${spring.rabbitmq.host}") String host, @Value("${spring.rabbitmq.port}") int port, @Value("${spring.rabbitmq.vhost}") String vHost) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(vHost);
        return connectionFactory;
    }

    @Bean
    public Queue logQueue() {
       return new Queue(appName);
    }

    @Bean
    Binding eventLogQueueBinding(Queue logQueue, TopicExchange exchange) {
        return BindingBuilder.bind(logQueue).to(exchange).with("*.*.*");
    }
}
