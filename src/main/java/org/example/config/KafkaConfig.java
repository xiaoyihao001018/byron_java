package org.example.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.dto.message.OperationLogMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration  // Spring配置类注解
public class KafkaConfig {
    
    // 定义秒杀主题名称常量
    public static final String SECKILL_TOPIC = "seckill-orders";
    
    // 创建Kafka主题
    @Bean
    public NewTopic seckillTopic() {
        return TopicBuilder.name(SECKILL_TOPIC)
                .partitions(1)    // 分区数
                .replicas(1)      // 副本数
                .build();
    }

    // 配置生产者工厂
    @Bean
    public ProducerFactory<String, OperationLogMessage> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        // Kafka服务器地址
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // 键的序列化器
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // 值的序列化器（使用JSON格式）
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    // 创建KafkaTemplate
    @Bean
    public KafkaTemplate<String, OperationLogMessage> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}