package org.twitterobserver.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.awaitility.Duration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.twitterobserver.config.TwitterConsumerConfiguration;
import org.twitterobserver.persistence.TweetRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StreamConsumerServiceITest {

	@Autowired
	private TwitterConsumerConfiguration consumerConfiguration;

	@Autowired
	private TweetRepository tweetRepository;

	@Test
	public void shouldConsumeAndPersistTweetsSatisfyingConfiguredCount_whenApplicationIsRan_givenSleepEqualToConfiguredTimeout() throws Exception {
		// This is bad practice but worked for quick sanity check
		// Sleeping thread in test is bad practice.
		// Also this test is performance dependent
		// Todo find a way to improve testability
		Thread.sleep(consumerConfiguration.getStreamReaderTimeout());

		long countOfPersistedTweets = tweetRepository.count();
		assertThat(countOfPersistedTweets).isEqualTo(consumerConfiguration.getMessageCountLimit());
	}
}