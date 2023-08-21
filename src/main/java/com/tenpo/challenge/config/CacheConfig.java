package com.tenpo.challenge.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class CacheConfig {
  @Bean
  public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
    Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
    RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig();
    // Specify the TTL for the 'longLived' cache
    cacheConfigurations.put("lastPercentage", defaultConfig.entryTtl(Duration.ofDays(3)));
    // Specify the TTL for the 'shortLived' cache
    cacheConfigurations.put("sum", defaultConfig.entryTtl(Duration.ofMinutes(30)));
    return RedisCacheManager.builder(connectionFactory)
        .cacheDefaults(defaultConfig)
        .withInitialCacheConfigurations(cacheConfigurations)
        .build();
  }
}
