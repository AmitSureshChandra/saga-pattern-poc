package com.github.AmitSureshChandra.paymentservice.config;

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
    String appName;

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
    public Queue paymentServiceQueue() {
        return new Queue(appName);
    }

    @Bean
    Binding OrderCancelEventBinding(Queue paymentServiceQueue, TopicExchange exchange) {
        return BindingBuilder.bind(paymentServiceQueue).to(exchange).with("payment-service.payment.order-cancel-event");
    }
}
