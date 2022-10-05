package com.ishan.bankingservice.accounts.adapters.config;

import com.ishan.bankingservice.accounts.domain.AccountEvent;
import java.util.Map;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String brokerUrls;

  public NewTopic accountsTopic() {
    return TopicBuilder.name(AccountEvent.TYPE)
        .partitions(10)
        .build();
  }

  public Map<String, Object> producerConfig() {
    return
        Map.of(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerUrls,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class
        );
  }

  @Bean
  public KafkaProducer<String, String> kafkaProducer() {
    return new KafkaProducer<>(producerConfig());
  }

}
