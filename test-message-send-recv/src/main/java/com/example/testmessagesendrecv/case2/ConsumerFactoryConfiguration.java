// package com.example.testmessagesendrecv.case2;

// import java.util.HashMap;
// import java.util.Map;
// import org.apache.kafka.clients.consumer.ConsumerConfig;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
// import org.springframework.kafka.core.ConsumerFactory;
// import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

// @Configuration
// public class ConsumerFactoryConfiguration {

//   @Value("${spring.kafka.bootstrap-servers}")
//   private String bootstrapServers;

//   @Value("${spring.kafka.consumer.group-id}")
//   private String groupId;

//   @Value("${kafka.topic}")
//   private String topic;

//   @Bean
//   public ConsumerFactory<String, String> consumerFactory() {
//     Map<String, Object> props = new HashMap<>();
//     props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//     props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
//     props.put(
//       ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
//       "org.apache.kafka.common.serialization.StringDeserializer"
//     );
//     props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//     props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
//     props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
//     props.put(ConsumerConfig.CLIENT_ID_CONFIG, "testConsumerFactoryID-01");

//     return new DefaultKafkaConsumerFactory<>(props);
//   }

//   @Bean
//   public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
//     ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//     factory.setConsumerFactory(consumerFactory());
//     return factory;
//   }
// }
