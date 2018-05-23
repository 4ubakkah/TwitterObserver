package org.twitterobserver.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.twitterobserver.persistence.TweetRepository;

@Service
@Slf4j
public class MetricsService {

	@Autowired
	private TweetRepository tweetRepository;

	public double calculateThroughput() {
		double messagesCount = tweetRepository.count();
		double secondsElapsed = (tweetRepository.findFirst1ByOrderByCreationDateDesc().getCreationDate() - tweetRepository.findFirst1ByOrderByCreationDateAsc()
				.getCreationDate()) / 1000;
		double messagesPerSecond = messagesCount / secondsElapsed;

		log.info(MarkerFactory.getMarker("metrics"), "Throughput during run is: {}", messagesPerSecond);

		return messagesPerSecond;
	}
}
