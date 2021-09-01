package com.zacharye.homework.subscribe;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import java.util.stream.IntStream;

/**
 * 将订阅者实例传递给Publisher.subscribe（订阅者）欧，将接收对onSubscribe(订阅）的调用，
 * 在调用Subscription.request(long)之前不会收到进一步的通知。
 * 发出信号后需求：
 * onNext(Object)的一次或多次调用，最多为Subscription.request(long)定义的最大数量
 * 单词调用onError(Throwable)或onComplete()，它指示终端状态，之后不在发送任何事件。
 *
 * 只要Subscriber实例能够处理更多内容，就可以通过Subscription.request(long) 发出需求信号
 */
@Slf4j
@Getter
public class BusinessSubscriber implements Subscriber {
  private Subscription subscription;
  private BusinessSubscriber[] businessSubscribers;

  public BusinessSubscriber() {}

  public BusinessSubscriber(int num) {
    businessSubscribers = new BusinessSubscriber[num];
    IntStream.range(0, num)
            .forEach(i -> {
              businessSubscribers[i] = new BusinessSubscriber();
            });
  }

  /**
   * 调用Publisher.subscribe(订阅者)后调用。
   * 在调用Subscription.request(long)之前，任何数据都不会开始流动。
   * 只要需要更多数据，此Subscriber实例就有责任调用Subscription.request(long).
   * 发布者仅在响应Subscription.request(long)时发送通知。
   * @param subscription
   */
  @Override
  public void onSubscribe(Subscription subscription) {
    this.subscription = subscription;
    log.info("订阅成功 {}", subscription);
    subscription.request(1L);

  }

  /**
   * 发布者为响应对Subscription.request(long)的请求而发送的数据通知。
   * @param o
   */
  @Override
  public void onNext(Object o) {
    log.info("接收到数据 {}", o);
    subscription.request(1L);
  }

  /**
   * 终端状态失败。
   * 即使再次调用Subscription.request(long) ,也不会发送更多事件
   * @param throwable
   */
  @Override
  public void onError(Throwable throwable) {
    log.error("出现异常 {}", throwable);
    subscription.cancel();
  }

  /**
   * 成功的终端状态。
   * 即使再次调用Subscription.request(long)，也不会发送更多事件
   *
   */
  @Override
  public void onComplete() {
    log.info("接收完所有数据！");
  }
}
