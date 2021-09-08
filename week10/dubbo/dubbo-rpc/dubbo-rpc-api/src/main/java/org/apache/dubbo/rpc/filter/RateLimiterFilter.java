package org.apache.dubbo.rpc.filter;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import java.time.Duration;
import java.util.function.Supplier;

@Activate(group = CommonConstants.PROVIDER)
public class RateLimiterFilter implements Filter, Filter.Listener {
    private static BulkheadConfig bulkheadConfig;
    private static BulkheadRegistry bulkheadRegistry;
    private static Bulkhead bulkhead;

    private static RateLimiterConfig rateLimiterConfig;
    private static RateLimiterRegistry rateLimiterRegistry;
    private static RateLimiter rateLimiter;

    static {
        bulkheadConfig = BulkheadConfig.custom()
            .maxConcurrentCalls(2)
            .maxWaitDuration(Duration.ofMillis(1))
            .build();
        bulkheadRegistry = BulkheadRegistry.of(bulkheadConfig);
        bulkhead = bulkheadRegistry.bulkhead("zachary");

        rateLimiterConfig = RateLimiterConfig.custom()
            .limitRefreshPeriod(Duration.ofMinutes(1))
            .limitForPeriod(2)
            .timeoutDuration(Duration.ofMinutes(1))
            .build();
        rateLimiterRegistry = RateLimiterRegistry.of(rateLimiterConfig);
        rateLimiter = rateLimiterRegistry.rateLimiter("zachary");
    }


    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        System.out.println(System.currentTimeMillis());
        //todo 添加限流逻辑 1 - Bulkhead
//        Supplier<Result> decoratedSupplier = Bulkhead.decorateSupplier(bulkhead,
//            () -> {
//               return invoker.invoke(invocation);
//            });
        //todo 添加限流逻辑 2 - RateLimiter
        Supplier<Result> decoratedSupplier2 = RateLimiter.decorateSupplier(rateLimiter,
            () -> {
                return invoker.invoke(invocation);
            });
        return decoratedSupplier2.get();
    }

    @Override
    public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {

    }

    @Override
    public void onError(Throwable t, Invoker<?> invoker, Invocation invocation) {

    }
}
