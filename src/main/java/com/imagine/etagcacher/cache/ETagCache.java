package com.imagine.etagcacher.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class ETagCache {

    private final Map<String, Cache<String, String>> eTagCache;

    @Autowired
    public ETagCache(final Map<String, Cache<String, String>> eTagCache) {
        this.eTagCache = eTagCache;
    }

    private Cache<String, String> getCache(final String cache) {
        eTagCache.computeIfAbsent(cache, c -> Caffeine.newBuilder().build());
        return eTagCache.get(cache);
    }

    public String get(final String cache,
                      final String key) {
        return getCache(cache).getIfPresent(key);
    }

    public void put(final String cache,
                    final String key,
                    final String value,
                    final int expiryTime) {
        if (!eTagCache.containsKey(cache)) {
            final Cache<String, String> newCache = Caffeine
                    .newBuilder()
                    .expireAfterWrite(expiryTime, TimeUnit.MINUTES)
                    .build();
            eTagCache.put(cache, newCache);
        }

        getCache(cache).put(key, value);
    }
}
