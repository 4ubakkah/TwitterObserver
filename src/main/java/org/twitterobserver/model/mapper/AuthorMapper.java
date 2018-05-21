package org.twitterobserver.model.mapper;

import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.twitterobserver.model.entity.AuthorEntity;

@Service
public class AuthorMapper {

	public AuthorEntity toEntity(TwitterProfile profile) {
		return AuthorEntity.builder().userId(profile.getId())
				.creationDate(profile.getCreatedDate().toInstant().toEpochMilli())
				.name(profile.getName())
				.screenName(profile.getScreenName())
				.build();
	}
}
