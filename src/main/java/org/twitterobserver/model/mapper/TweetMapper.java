package org.twitterobserver.model.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.twitterobserver.model.entity.TweetEntity;

@Service
public class TweetMapper {

	@Autowired
	private AuthorMapper authorMapper;

	public TweetEntity toEntity(org.springframework.social.twitter.api.Tweet tweet) {
		return TweetEntity.builder().messageId(tweet.getIdStr())
				.creationDate(tweet.getCreatedAt().toInstant().toEpochMilli())
				.messageContent(tweet.getText())
				.author(authorMapper.toEntity(tweet.getUser()))
				.build();
	}
}
