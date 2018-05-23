package org.twitterobserver.service;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.twitterobserver.model.entity.AuthorEntity;
import org.twitterobserver.model.entity.AuthorFixture;
import org.twitterobserver.model.event.ConsumptionFinishedPersistedEvent;
import org.twitterobserver.model.event.OutputProducedEvent;
import org.twitterobserver.persistence.AuthorRepository;

@RunWith(MockitoJUnitRunner.class)
public class ProcessorServiceTest {

	@InjectMocks
	private ProcessorService processorService;

	@Mock
	private ProducerService producerService;

	@Mock
	private AuthorRepository authorRepo;

	@Mock
	private ApplicationEventPublisher publisher;

	@Mock
	private MetricsService metricsService;

	@Test
	public void shouldDelegate_whenOnApplicationEvent() throws Exception {
		List<AuthorEntity> expectedRetrievedList = Arrays.asList(AuthorFixture.anAuthor());
		when(authorRepo.findAllByOrderByCreationDateAsc()).thenReturn(expectedRetrievedList);

		processorService.onApplicationEvent(new ConsumptionFinishedPersistedEvent(this));

		verify(authorRepo).findAllByOrderByCreationDateAsc();
		verify(producerService).print(expectedRetrievedList);
		verify(publisher).publishEvent(any(OutputProducedEvent.class));
		verify(metricsService).calculateThroughput();
	}

}