package com.example.order_api.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class MessagingConfig {


    public static  final  String Exchange="order_exchange";


    @Bean
    public Queue getEmailQueue()
    {
        return new Queue("email_queue");
    }


    @Bean
    public Queue getCartQueue()
    {
        return new Queue("remove_cart_queue");
    }

    @Bean
    public TopicExchange exchange()
    {
        return new TopicExchange(Exchange);
    }

    @Bean
    public Binding emailBinding(TopicExchange exchange)
    {
        return BindingBuilder.bind(getEmailQueue()).to(exchange).with(getEmailQueue().getName());

    }
    @Bean
    public Binding cartBinding(TopicExchange exchange)
    {
        return BindingBuilder.bind(getCartQueue()).to(exchange).with(getCartQueue().getName());

    }

    @Bean
    public MessageConverter converter()
    {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory)
    {
        RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

}
