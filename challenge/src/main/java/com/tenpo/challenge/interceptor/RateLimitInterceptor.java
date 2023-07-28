package com.tenpo.challenge.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

  private static final int MAX_REQUESTS_PER_MINUTE = 3;
  /**
   * La key es fija para que el rate de 3 por minuto sea para cualquier request
   */
  private static final String REDIS_KEY = "rateLimit:requests";
  /**
   * Utilizo redis ya que puede existir mas de una instancia de la aplicacion
   */
  private final StringRedisTemplate redisTemplate;

  public RateLimitInterceptor(StringRedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @Override
  public boolean preHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler
  ) throws Exception {
    var currentValue = this.redisTemplate.opsForValue()
        .increment(REDIS_KEY, 1);
    if (currentValue == null) { // Decido no dejar pasar si me devuelve null
      return false;
    }
    if (currentValue == 1) {
      redisTemplate.expire(REDIS_KEY, 1, TimeUnit.MINUTES);
    }
    if (currentValue > MAX_REQUESTS_PER_MINUTE) {
      response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
      response.getWriter().write(HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());
      return false;
    } else {
      return true;
    }
  }
}
