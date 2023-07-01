package com.example.testmessagesendrecv.case2;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class KafkaMessageReactiveConsumer {

  private final ReactiveKafkaConsumerTemplate<String, String> reactiveKafkaConsumerTemplate;

  public void startConsumingMessages() {
    Flux<ConsumerRecord<String, String>> messageStream = reactiveKafkaConsumerTemplate.receiveAutoAck();

    messageStream.subscribe(consumerRecord -> {
      String key = consumerRecord.key();
      String message = consumerRecord.value();
      String topic = consumerRecord.topic();

      // 받은 메시지 처리 로직을 작성
      System.out.println("key: " + key);
      System.out.println("Received message: " + message);
      System.out.println("Topic: " + topic);
    });
  }
}
