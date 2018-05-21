package org.twitterobserver.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

@Configuration
public class TwitterConsumerConfiguration {

	@Value("${social.twitter.appId}")
	private String consumerKey;

	@Value("${social.twitter.appSecret}")
	private String consumerSecret;

	@Value("${social.twitter.token}")
	private String accessToken;

	@Value("${social.twitter.tokenSecret}")
	private String accessTokenSecret;

	@Value("${social.twitter.messagesCountLimit}")
	@Getter
	private int messageCountLimit;

	@Value("${social.twitter.trackedKeyword}")
	@Getter
	private String trackedKeyword;


	@Value("${social.twitter.streamReaderTimeout}")
	@Getter
	private int streamReaderTimeout;

	@Bean
	public TwitterTemplate getTwitterTemplate(){
		return new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
	}
}
