package com.example.testmessagesendrecv.case1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class SendMessageController {

  private final KafkaMessageProducer kafkaMessageProducer;
  private final KafkaMessageReactiveProducer kafkaMessageReactiveProducer;

  @GetMapping(path = "/sendMessage")
  public Mono<String> sendMessage() {
    kafkaMessageProducer.sendMessage("case1 sendMessage Test!");

    return Mono.just("send message ok");
  }

  @GetMapping(path = "/sendMessage2")
  public Mono<String> sendMessage2() {
    kafkaMessageReactiveProducer.sendMessage("case1 sendMessage Reactive Test!");

    return Mono.just("send message ok");
  }

  @GetMapping(path = "/sendMessage3")
  public Mono<String> sendConvertMonoMessage() {
    kafkaMessageProducer.sendConvertMonoMessage("case1 sendConvertMonoMessage Test!");

    return Mono.just("send convert mono message ok");
  }
}
