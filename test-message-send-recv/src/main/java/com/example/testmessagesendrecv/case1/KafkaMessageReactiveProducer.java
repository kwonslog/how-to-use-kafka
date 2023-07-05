package com.example.testmessagesendrecv.case1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;
import reactor.kafka.sender.TransactionManager;

@Slf4j
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

  public void sendMessage2(String message) {
    Mono<SenderResult<Void>> producer = reactiveKafkaProducerTemplate.send(
      new ProducerRecord<>(topic, "keyIsTest", message)
    );
    producer
      .doOnSuccess(senderResult -> {
        log.debug("success topic:{}", senderResult.recordMetadata().topic());
        log.debug("success offset:{}", senderResult.recordMetadata().offset());
        log.debug("success partition:{}", senderResult.recordMetadata().partition());
      })
      .doOnError(error -> {
        log.error("failed message:{}", error.getMessage());
      })
      .subscribe();
  }

  public void sendMessageCommit(String message) {
    TransactionManager transactionManager = reactiveKafkaProducerTemplate.transactionManager();

    transactionManager
      .begin()
      .then(reactiveKafkaProducerTemplate.send(new ProducerRecord<>(topic, "keyIsTest", message)))
      .flatMap(senderResult -> {
        log.debug("success topic:{}", senderResult.recordMetadata().topic());
        log.debug("success offset:{}", senderResult.recordMetadata().offset());
        log.debug("success partition:{}", senderResult.recordMetadata().partition());
        return transactionManager.commit();
      })
      .doOnError(error -> {
        log.error("failed message:{}", error.getMessage());
        transactionManager.abort();
      })
      .subscribe();
  }
}
