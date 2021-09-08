package org.apache.dubbo.demo.provider;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.vavr.control.Try;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.demo.DemoService;
import org.apache.dubbo.rpc.*;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.function.Supplier;

@Activate(group = CommonConstants.PROVIDER)
public class RateLimiterFilter implements Filter, Filter.Listener {
    @Resource
    private DemoService demoService;

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        //todo 添加限流逻辑
        BulkheadConfig config = BulkheadConfig.custom()
            .maxConcurrentCalls(2)
            .maxWaitDuration(Duration.ofMillis(1))
            .build();
        BulkheadRegistry bulkheadRegistry = BulkheadRegistry.of(config);
        Bulkhead bulkhead = bulkheadRegistry.bulkhead("name");
        Supplier<String> decoratedSupplier = Bulkhead.decorateSupplier(bulkhead,
            new Supplier<String>() {
                @Override
                public String get() {
                    return demoService.sayHello("bulkhead");
                }
            });
        String result = Try.ofSupplier(decoratedSupplier)
            .recover(throwable -> "Hello from Recovery").get();
        return invoker.invoke(invocation);
    }

    @Override
    public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {

    }

    @Override
    public void onError(Throwable t, Invoker<?> invoker, Invocation invocation) {

    }
}
