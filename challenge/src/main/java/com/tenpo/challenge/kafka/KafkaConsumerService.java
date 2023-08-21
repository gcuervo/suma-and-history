package com.tenpo.challenge.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.challenge.model.dto.ApiCallHistoryMessage;
import com.tenpo.challenge.service.ApiCallHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

  private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

  private final ObjectMapper mapper;
  private final ApiCallHistoryService historyService;

  public KafkaConsumerService(ObjectMapper mapper, ApiCallHistoryService historyService) {
    this.mapper = mapper;
    this.historyService = historyService;
  }

  @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
  public void consumeMessage(String message) {
    logger.info("Message consumed: " + message);
    try {
      var history = mapper.readValue(message, ApiCallHistoryMessage.class);
      historyService.saveApiCallHistory(history);
    } catch (JsonProcessingException e) {
      logger.error("Error consuming message from kafka");
      throw new RuntimeException(e);
    }
  }

  @KafkaListener(topics = "operations.DLT")
  public void dltListener(String in) {
    logger.error("Received from DLT: {}", in);
    // Habría que manejar el error de forma adecuada
  }
}
