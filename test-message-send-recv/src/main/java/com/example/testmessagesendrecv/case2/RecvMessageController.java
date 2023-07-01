package com.example.testmessagesendrecv.case2;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class RecvMessageController {

  private final KafkaMessageReactiveConsumer kafkaMessageReactiveConsumer;

  @GetMapping(path = "/recvMessage")
  public Mono<String> recvMessage() {
    kafkaMessageReactiveConsumer.startConsumingMessages();

    return Mono.just("receive Message ok");
  }
}
