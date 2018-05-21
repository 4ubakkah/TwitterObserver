package org.twitterobserver.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.social.twitter.api.Tweet;
import org.twitterobserver.config.TwitterConsumerConfiguration;
import org.twitterobserver.model.TweetFixture;
import org.twitterobserver.model.entity.TweetEntity;
import org.twitterobserver.model.event.ConsumptionEndedEvent;
import org.twitterobserver.model.mapper.TweetMapper;
import org.twitterobserver.persistence.TweetRepository;

@RunWith(MockitoJUnitRunner.class)
public class PersisterServiceTest {

	private static final int MESSAGE_COUNT_THRESHOLD = 1;

	@InjectMocks
	private PersisterService persisterService;

	@Mock
	private TwitterConsumerConfiguration configuration;

	@Mock
	private TweetRepository tweetRepository;

	@Mock
	private TweetMapper tweetMapper;

	@Mock
	private ApplicationEventPublisher applicationEventPublisher;


	@Test
	public void shouldProcessTweet_whenProcessTweet_givenMessageCountThresholdIsNotHit() throws Exception {
		Tweet tweet = TweetFixture.regular();
		TweetEntity tweetEntity = TweetEntity.builder().build();
		when(tweetMapper.toEntity(tweet)).thenReturn(tweetEntity);
		when(configuration.getMessageCountLimit()).thenReturn(MESSAGE_COUNT_THRESHOLD);
		when(tweetRepository.count()).thenReturn(Long.valueOf(MESSAGE_COUNT_THRESHOLD - 1));

		persisterService.processTweet(tweet);

		verify(tweetRepository).save(tweetEntity);
		verifyZeroInteractions(applicationEventPublisher);
	}

	@Test
	public void shouldFinishConsumption_whenProcessTweet_givenMessageCountThresholdIsHit() throws Exception {
		Tweet tweet = TweetFixture.regular();
		when(configuration.getMessageCountLimit()).thenReturn(MESSAGE_COUNT_THRESHOLD);
		when(tweetRepository.count()).thenReturn(Long.valueOf(MESSAGE_COUNT_THRESHOLD));

		persisterService.processTweet(tweet);

		verify(tweetRepository).count();
		verifyNoMoreInteractions(tweetRepository);
		verify(applicationEventPublisher).publishEvent(any(ConsumptionEndedEvent.class));
	}
}