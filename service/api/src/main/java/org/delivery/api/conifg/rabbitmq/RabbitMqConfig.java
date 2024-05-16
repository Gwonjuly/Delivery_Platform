package org.delivery.api.conifg.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    /**
     * Producer: API Server
     * 메세지를 생성 후. Exchange에 전달
     * 메세지를 Exchange -> Queue 전달
     */

    //Exchange 생성
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("delivery.exchange");
    }

    //Queue 생성
    @Bean
    public Queue queue(){
        return new Queue("delivery.queue");
    }

    //Queue와 exchange 바인딩(연결)
    @Bean
    public Binding binding(DirectExchange directExchange, Queue queue){
        return BindingBuilder.bind(queue).to(directExchange).with("delivery.key");
    }

    /**
     * yaml 설정 후, RabbitTemplate에서 ConnectionFactory가 자동으로 만들어짐
     * RabbitTemplate:Producer에서 Exchange로 넘기기 위한 템플릿
     * MessageConverter: Object <-> Json
     */
    //Exchange에 데이터 전송을 위한 템플릿: RabbitTemplate
    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory, //yaml에서 설정
            MessageConverter messageConverter //object <-> Json
    ){
        var rabbitTemplate=new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper){
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
