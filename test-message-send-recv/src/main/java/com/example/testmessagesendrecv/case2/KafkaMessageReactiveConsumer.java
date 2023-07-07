package com.example.testmessagesendrecv.case2;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.ReceiverRecord;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageReactiveConsumer {

  private final ReactiveKafkaConsumerTemplate<String, String> reactiveKafkaConsumerTemplate;

  @PostConstruct
  public void init() {
    log.info("#### Kafka 메세지 수신을 시작 합니다.");
    startConsumingMessages();
  }

  //이 메소드는 한번 실행하면 이후 메세지를 계속해서 수신한다.
  //결국 서버가 가동되고 나서 한번 실행 할 수 있도록 처리가 필요하다.
  public void startConsumingMessages() {
    //자동커밋 할때 사용.
    // Flux<ConsumerRecord<String, String>> messageStream = reactiveKafkaConsumerTemplate.receiveAutoAck();
    //수동 커밋 할때 사용.
    Flux<ReceiverRecord<String, String>> messageStream = reactiveKafkaConsumerTemplate.receive();

    messageStream.subscribe(consumerRecord -> {
      String key = consumerRecord.key();
      String message = consumerRecord.value();
      String topic = consumerRecord.topic();
      long offset = consumerRecord.offset();
      int partition = consumerRecord.partition();

      // 받은 메시지 처리 로직을 작성
      log.debug("key: {}", key);
      log.debug("partition: {}", partition);
      log.debug("offset: {}", offset);
      log.debug("Received message: {}", message);
      log.debug("Topic: {}", topic);

      //커밋 처리시 kafka 브로커 서버의 __consumer_offsets 토픽에 오프셋 값이 저장된다.
      consumerRecord.receiverOffset().commit().subscribe(t -> log.debug("수동 커밋 완료"));
    });
  }
}
