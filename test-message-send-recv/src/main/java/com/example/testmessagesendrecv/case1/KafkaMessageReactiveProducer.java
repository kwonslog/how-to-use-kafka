package com.example.testmessagesendrecv.case1;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaMessageReactiveProducer {

  /*
   * Reactive Kafka 라이브러리를 사용하여 메세지 송신 코드를 작성.
   */
  private final ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate;

  @Value("${kafka.topic}")
  private String topic;

  public void sendMessage(String message) {
    reactiveKafkaProducerTemplate
      .send(topic, message)
      .doOnSuccess(sendResult -> {
        // 메시지 송신 성공 처리
        System.out.println("Message sent successfully: " + sendResult.recordMetadata().offset());
      })
      .doOnError(error -> {
        // 메시지 송신 실패 처리
        System.err.println("Failed to send message: " + error.getMessage());
      })
      .then()
      .subscribe();
  }
}
