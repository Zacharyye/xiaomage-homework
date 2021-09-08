## **作业**

通过 Dubbo Filter 机制实现 Dubbo 服务提供方限流，利用 resilience4j 来整合实现，具体参考文档:https://resilience4j.readme.io/docs/bulkhead

1.BulkHead实现限流

- 设定1ms内最多允许2个请求通过，其他请求被阻塞，提示如下：

  `io.github.resilience4j.bulkhead.BulkheadFullException: Bulkhead 'zachary' is full and does not permit further calls`

2.RateLimiter实现限流

- 设定1分钟内最多允许两个请求通过，其他请求被阻塞，提示如下：

  `io.github.resilience4j.ratelimiter.RequestNotPermitted: RateLimiter 'zachary' does not permit further calls`

过滤器代码地址：
