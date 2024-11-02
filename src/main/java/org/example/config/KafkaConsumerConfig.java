package org.example.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.dto.message.OperationLogMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

/**
 * Kafka消费者配置类
 * 用于配置Kafka消费者的各种属性和行为
 */
@Configuration
public class KafkaConsumerConfig {

    /**
     * 创建Kafka消费者工厂
     * 该工厂负责创建Kafka消费者实例
     * @return 返回配置好的消费者工厂实例
     */
    @Bean
    public ConsumerFactory<String, OperationLogMessage> consumerFactory() {
        // 创建配置属性映射
        Map<String, Object> props = new HashMap<>();
        
        // 设置Kafka服务器地址
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        
        // 设置消费者组ID，同一组的消费者共同消费消息
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "example-group");
        
        // 设置键的反序列化器为String类型
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        
        // 设置值的反序列化器为JSON类型，用于处理复杂对象
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        
        // 设置信任的包路径，用于反序列化安全性控制
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "org.example.dto.message");
        
        // 设置默认的消息体类型
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "org.example.dto.message.OperationLogMessage");
        
        // 创建并返回默认的Kafka消费者工厂
        return new DefaultKafkaConsumerFactory<>(props);
    }

    /**
     * 创建Kafka监听器容器工厂
     * 用于创建处理@KafkaListener注解的容器
     * @return 返回配置好的监听器容器工厂
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OperationLogMessage> kafkaListenerContainerFactory() {
        // 创建并发的Kafka监听器容器工厂
        ConcurrentKafkaListenerContainerFactory<String, OperationLogMessage> factory = 
            new ConcurrentKafkaListenerContainerFactory<>();
            
        // 设置消费者工厂
        factory.setConsumerFactory(consumerFactory());
        
        return factory;
    }
} 