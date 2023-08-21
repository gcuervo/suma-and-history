package com.tenpo.challenge.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.challenge.model.dto.ApiCallHistoryMessage;
import com.tenpo.challenge.model.entity.ApiCallHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

  private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

  @Value("${kafka.topic.name}")
  private String topicName;
  private final ObjectMapper mapper;
  private final KafkaTemplate<String, String> kafkaTemplate;

  public KafkaProducerService(ObjectMapper mapper, KafkaTemplate<String, String> kafkaTemplate) {
    this.mapper = mapper;
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendMessage(ApiCallHistoryMessage history) {
    try {
      kafkaTemplate.send(topicName, mapper.writeValueAsString(history));
    } catch (JsonProcessingException e) {
      logger.error("Error sending message to kafka");
    }
  }
}
