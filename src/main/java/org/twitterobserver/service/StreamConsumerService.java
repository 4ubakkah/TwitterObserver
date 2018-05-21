package org.twitterobserver.service;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.social.twitter.api.Stream;
import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.StreamWarningEvent;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;
import org.twitterobserver.config.TwitterConsumerConfiguration;
import org.twitterobserver.model.event.ConsumptionEndedEvent;
import org.twitterobserver.model.event.ConsumptionFinishedPersistedEvent;

@Service
@Slf4j
public class StreamConsumerService {

	private Stream twitterStream;
	private TimerTask timerTask;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private TwitterConsumerConfiguration configuration;

	@Autowired
	private TwitterTemplate twitterTemplate;

	@Autowired
	private PersisterService persisterService;

	public void startConsumption() {
		twitterStream = twitterTemplate.streamingOperations().filter(configuration.getTrackedKeyword(), Arrays.asList(streamListener()));

		timerTask = new TimerTask() {
			@Override
			public void run() {
				log.info("Finished stream consumption due timeout. Timeout [{}]", configuration.getStreamReaderTimeout());
				applicationEventPublisher.publishEvent(new ConsumptionEndedEvent(this));
			}
		};

		new Timer().schedule(timerTask, configuration.getStreamReaderTimeout());
		log.info("Scheduled consumption timeout set to [{}]", configuration.getStreamReaderTimeout());
	}

	private StreamListener streamListener() {
		return new StreamListener() {
			public void onTweet(Tweet tweet) {
				persisterService.processTweet(tweet);
			}

			public void onWarning(StreamWarningEvent warningEvent) {
				// intentionally left blank, not used
			}

			public void onLimit(int numberOfLimitedTweets) {
				// intentionally left blank, not used
			}

			public void onDelete(StreamDeleteEvent deleteEvent) {
				// intentionally left blank, not used
			}
		};
	}

	@EventListener
	public void onApplicationEvent(ConsumptionEndedEvent consumptionEndedEvent) {
		timerTask.cancel();
		twitterStream.close();
		applicationEventPublisher.publishEvent(new ConsumptionFinishedPersistedEvent(this));
	}
}
