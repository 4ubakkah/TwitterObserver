package org.twitterobserver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Service;
import org.twitterobserver.config.TwitterConsumerConfiguration;
import org.twitterobserver.model.entity.TweetEntity;
import org.twitterobserver.model.event.ConsumptionEndedEvent;
import org.twitterobserver.model.mapper.TweetMapper;
import org.twitterobserver.persistence.TweetRepository;

@Service
@Slf4j
public class PersisterService {

	@Autowired
	private TweetRepository tweetRepository;

	@Autowired
	private TwitterConsumerConfiguration configuration;

	@Autowired
	private TweetMapper tweetMapper;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	public void processTweet(Tweet tweet) {
		if (tweetRepository.count() < configuration.getMessageCountLimit()) {
			log.info("Received new tweet: {}", tweet.getText());
			TweetEntity tweetEntity = tweetMapper.toEntity(tweet);
			tweetRepository.save(tweetEntity);
		} else {
			log.info("Finished stream consumption due message count limit being reached. Limit [{}]", configuration.getMessageCountLimit());
			applicationEventPublisher.publishEvent(new ConsumptionEndedEvent(this));
		}
	}
}
