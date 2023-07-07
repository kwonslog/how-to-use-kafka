package com.example.testmessagesendrecv.case2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaMessageConsumer {

  //메세지 수신처가 2군데라서 이곳은 주석처리함
  //@KafkaListener(topics = "test-topic", concurrency = "1")
  public void listen(String message) throws InterruptedException {
    log.debug("Processing message: {}", message);
  }
}
