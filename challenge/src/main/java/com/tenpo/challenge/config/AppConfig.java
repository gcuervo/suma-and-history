package com.tenpo.challenge.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import java.util.TimeZone;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableRetry
@EnableJpaRepositories(basePackages = "com.tenpo.*.repository")
@ComponentScan("com.tenpo.challenge")
@EnableCaching
@EnableAsync
@Configuration
public class AppConfig implements WebMvcConfigurer {

  @PostConstruct
  public void setup() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }

  @Bean
  public ObjectMapper getMapper() {
    return Jackson2ObjectMapperBuilder.json()
        .featuresToDisable(SerializationFeature.WRITE_DATES_WITH_CONTEXT_TIME_ZONE)
        .modules(new JavaTimeModule())
        .dateFormat(new StdDateFormat().withColonInTimeZone(true))
        .build();
  }
}
