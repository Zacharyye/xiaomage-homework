package com.zacharye.homework;

import com.zacharye.homework.publish.SimplePublisher;
import com.zacharye.homework.subscribe.BusinessSubscriber;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.stream.IntStream;

/**
 * 参考网上资料，总感觉哪里不对劲
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PublisherTest {

  @Test
  public void testPublish() {
    SimplePublisher publisher = new SimplePublisher();
    publisher.subscribe(new BusinessSubscriber(5));
    IntStream.rangeClosed(1, 5)
            .forEach(i -> {
              publisher.publish(i);
            });

    IntStream.rangeClosed(6, 10)
            .forEach(i -> {
              publisher.publish(i);
            });
  }
}
