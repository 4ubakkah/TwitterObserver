package org.twitterobserver.service;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.twitterobserver.model.entity.AuthorEntity;
import org.twitterobserver.model.event.ConsumptionFinishedPersistedEvent;
import org.twitterobserver.model.event.OutputProducedEvent;
import org.twitterobserver.persistence.AuthorRepository;
import org.twitterobserver.persistence.TweetRepository;

@Service
@Slf4j
public class ProcessorService {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private ProducerService producerService;

	@Autowired
	private MetricsService metricsService;

	@Autowired
	private AuthorRepository authorRepo;

	@EventListener
	public void onApplicationEvent(ConsumptionFinishedPersistedEvent consumptionFinishedEvent) {
		metricsService.calculateThroughput();

		List<AuthorEntity> allAuthors = authorRepo.findAllByOrderByCreationDateAsc();
		producerService.print(allAuthors);
		applicationEventPublisher.publishEvent(new OutputProducedEvent(this));
	}
}
