package org.example.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.dto.message.SeckillMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTestConfig {

    /**
     * 配置Kafka生产者工厂
     */
    @Bean
    public ProducerFactory<String, SeckillMessage> seckillProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        // 配置Kafka服务器地址
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // 配置key和value的序列化器
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    /**
     * 配置KafkaTemplate用于发送消息
     */
    @Bean
    public KafkaTemplate<String, SeckillMessage> seckillKafkaTemplate() {
        return new KafkaTemplate<>(seckillProducerFactory());
    }
} 