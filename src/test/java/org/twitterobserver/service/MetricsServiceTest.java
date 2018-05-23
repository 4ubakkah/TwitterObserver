package org.twitterobserver.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.twitterobserver.model.entity.TweetEntity;
import org.twitterobserver.model.entity.TweetEntityFixture;
import org.twitterobserver.persistence.TweetRepository;

@RunWith(MockitoJUnitRunner.class)
public class MetricsServiceTest {

	@InjectMocks
	private MetricsService metricsService;

	@Mock
	private TweetRepository tweetRepository;

	@Test
	public void shouldCalculateThroughputUsingTweets_whenCalculateThroughput() throws Exception {
		Double messagesCount = 10000D;
		TweetEntity tweetFirst = TweetEntityFixture.withCreationDate(1527110693L);
		TweetEntity tweetLast = TweetEntityFixture.withCreationDate(1527130693L);

		when(tweetRepository.count()).thenReturn(messagesCount.longValue());
		when(tweetRepository.findFirst1ByOrderByCreationDateAsc()).thenReturn(tweetFirst);
		when(tweetRepository.findFirst1ByOrderByCreationDateDesc()).thenReturn(tweetLast);

		double throughput = metricsService.calculateThroughput();

		assertThat(throughput).isEqualTo(messagesCount / (tweetLast.getCreationDate() - tweetFirst.getCreationDate()) * 1000);
	}
}