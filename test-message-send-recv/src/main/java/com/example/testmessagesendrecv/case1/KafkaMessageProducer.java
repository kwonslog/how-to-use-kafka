package com.example.testmessagesendrecv.case1;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@RequiredArgsConstructor
public class KafkaMessageProducer {

  /*
   * KafkaTemplate 을 사용하여 메세지 송신 처리를 구현하였다.
   * 하지만 webflux 기반의 프로젝트 구성이라면 Reactive Kafka 를 사용하여
   * 메세지 송신 처리를 할 필요가 있다.
   */
  private final KafkaTemplate<String, String> kafkaTemplate;

  @Value("${kafka.topic}")
  private String topic;

  public void sendMessage(String message) {
    ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);

    ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(record);
    future.addCallback(
      new ListenableFutureCallback<SendResult<String, String>>() {
        @Override
        public void onSuccess(SendResult<String, String> result) {
          // 메시지 전송 성공 처리
          System.out.println("Message sent successfully: " + result.getRecordMetadata().offset());
        }

        @Override
        public void onFailure(Throwable ex) {
          // 메시지 전송 실패 처리
          System.err.println("Failed to send message: " + ex.getMessage());
        }
      }
    );
  }
}
