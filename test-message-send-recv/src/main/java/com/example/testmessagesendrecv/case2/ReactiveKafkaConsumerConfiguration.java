package com.example.testmessagesendrecv.case2;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import reactor.kafka.receiver.ReceiverOptions;

@Configuration
public class ReactiveKafkaConsumerConfiguration {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Value("${spring.kafka.consumer.group-id}")
  private String groupId;

  @Value("${kafka.topic}")
  private String topic;

  @Bean
  public ReactiveKafkaConsumerTemplate<String, String> reactiveKafkaConsumerTemplate() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
    props.put(
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
      "org.apache.kafka.common.serialization.StringDeserializer"
    );
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
    props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
    props.put(ConsumerConfig.CLIENT_ID_CONFIG, "testConsumerId-01");
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    ReceiverOptions<String, String> receiverOptions = ReceiverOptions
      .<String, String>create(props)
      //수신 받을 토픽을 설정함.
      .subscription(Collections.singleton(topic)) //수동 커밋 처리를 위해 아래 값을 설정함.
      //이 값들이 설정되고 나면 수동으로 커밋을 해야만 메세지를 수신 할 수 있다.
      .commitInterval(Duration.ZERO)
      .commitBatchSize(0);

    return new ReactiveKafkaConsumerTemplate<>(receiverOptions);
  }
}
