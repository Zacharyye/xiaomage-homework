package com.zacharye.homework.controller;

import com.zacharye.homework.publish.SimplePublisher;
import com.zacharye.homework.subscribe.BusinessSubscriber;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/")
@Slf4j
public class HelloController {

  @GetMapping("mono")
  public Mono<Object> mono() {
//    return Mono.just("hello webflux");
    return Mono.create(monoSink -> {
      log.info("创建Mono");
      monoSink.success("hello webflux");
    }).doOnSubscribe(subscription -> {
      log.info("{}", subscription);
    }).doOnNext(o -> {
      log.info("{}", o);
    });
  }

  @GetMapping("flux")
  public Flux<String> flux() {
    return Flux.just("hello", "webflux", "spring", "boot");
  }

  public void subscribe() {
    SimplePublisher simplePublisher = new SimplePublisher();
    Mono.from(simplePublisher).subscribe(new BusinessSubscriber());
  }
}
