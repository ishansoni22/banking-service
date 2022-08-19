package com.ishan.bankingservice.iam.adapters.config;

import com.ishan.bankingservice.iam.domain.UserEvent;
import java.util.HashMap;
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
  private String kafkaBootstrapUrl;

  @Bean
  public NewTopic usersTopic() {
    return TopicBuilder.name(UserEvent.TYPE)
        /*.partitions(6)*/
        .build();
  }

  /*
  Producer Configuration
   */
  private Map<String, Object> producerConfig() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapUrl);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    return props;
  }

  /*
  Create the Producer
   */
  @Bean
  public KafkaProducer<String, String> producer() {
    return new KafkaProducer<>(producerConfig());
  }

}
