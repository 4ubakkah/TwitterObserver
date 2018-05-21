package org.twitterobserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProducerService {

	@Autowired
	private ObjectMapper jsonMapper;

	public void print(Object object) {
		try {
			log.info("Result: {}", jsonMapper.writeValueAsString(object));
		} catch (JsonProcessingException e) {
			log.error("Error occurred while printing results.", e);
		}
	}
}
