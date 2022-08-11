package com.assist.internship.migrationservice.config;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

import static java.util.Arrays.asList;

public class CachingConfig implements CacheManagerCustomizer<ConcurrentMapCacheManager>
{
    @Override
    public void customize(ConcurrentMapCacheManager cacheManager) {
        cacheManager.setCacheNames(asList("movies"));
    }
}