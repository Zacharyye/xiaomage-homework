/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.geektimes.cache.interceptor;

import org.geektimes.interceptor.AnnotatedInterceptor;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheRemove;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * @author zhouyin
 * @since 2021-08-04
 */
@Interceptor
public class CacheRemoveInterceptor extends AnnotatedInterceptor<CacheRemove> {

    private CachingProvider cachingProvider = Caching.getCachingProvider();

    private CacheManager cacheManager = cachingProvider.getCacheManager();

    @Override
    protected Object execute(InvocationContext context, CacheRemove cacheRemove) throws Throwable {
        String cacheName = cacheRemove.cacheName();
        Cache cache = getCache(cacheName);
        boolean afterInvocation = cacheRemove.afterInvocation();
        Object result = context.proceed();
        if(afterInvocation) {
            Object[] parameters = context.getParameters();
            Object key = parameters[0];
            cache.remove(key);
        }
        return result;
    }

    private Cache getCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            cache = cacheManager.createCache(cacheName,
                    new MutableConfiguration().setTypes(Object.class, Object.class));
        }
        return cache;
    }


}
