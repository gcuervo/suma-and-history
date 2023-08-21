package com.tenpo.challenge.config;

import com.tenpo.challenge.interceptor.RateLimitInterceptor;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

  private final StringRedisTemplate redisTemplate;


  public MvcConfiguration(StringRedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new RateLimitInterceptor(redisTemplate))
        .excludePathPatterns(List.of("/swagger-ui/**", "/v3/api-docs"))
        .addPathPatterns("/api/challenge/**");
  }
}
