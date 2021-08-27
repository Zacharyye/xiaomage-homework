package com.zacharye.homework.publish;

import com.zacharye.homework.subscribe.BusinessSubscriber;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * 发布者是可能无限数量的有序元素的提供者，根据其从订阅者收到的需求发布他们。
 * 发布者可以在不同的时间点动态地为多个订阅者订阅
 */
@Slf4j
public class SimplePublisher implements Publisher {
  private Subscriber[] subscribers;

  /**
   * 订阅表示订阅发布者的订阅者的一对一生命周期。
   * 它只能由一个订阅者使用一次
   * 它用于表示对数据的需求和取消需求（并允许资源清理）。
   */
  private Subscription subscription = new Subscription() {
    /**
     * 在通过此方法发出需求信号之前，发布者不会发送任何事件。
     * 然而，无论何时需要，它都可以被调用 - 但是未完成的累积需求绝不能超过Long.MAX_VALUE.
     *  Long.MAX_VALUE的未完成累积需求可能会被发布者视为"有效无限制"。
     * 发布者可以发送任何请求，因此只需要发出可以安全处理的信号需求。
     * 如果流结束但发布者可以发送少于请求的发送者，但必须发出Subscriber.onError(Throwable)或Subscriber.onComplete().
     *
     * @param l
     */
    @Override
    public void request(long l) {
      log.info("叮 {}", l);
    }

    /**
     * 请求发布者停止发送数据并清理资源。
     * 在呼叫取消后，仍然可以发送数据以满足先前发出的信号要求。
     */
    @Override
    public void cancel() {

    }
  };

  /**
   * 请求Publisher启动流数据。
   * 这是一个"工厂方法"，可以多次调用，每次启动一个新的订阅。
   * 每个订阅仅适用于一个订阅者。
   * 订阅者只应订阅一个发布者一次。
   * 如果发布者拒绝订阅尝试或者以其他方式失败，它将通过Subscriber.onError发出错误信号
   * @param subscriber
   */
  @Override
  public void subscribe(Subscriber subscriber) {
    BusinessSubscriber businessSubscriber = (BusinessSubscriber) subscriber;
    BusinessSubscriber[] businessSubscribers = businessSubscriber.getBusinessSubscribers();
    for (BusinessSubscriber businessSubscriber1 : businessSubscribers) {
      businessSubscriber1.onSubscribe(subscription);
    }
    this.subscribers = businessSubscribers;
  }

  public void publish(Object msg) {
    for (Subscriber subscriber : this.subscribers) {
      subscriber.onNext(msg);
    }
  }
}
