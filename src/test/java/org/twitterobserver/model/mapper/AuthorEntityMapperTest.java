package org.twitterobserver.model.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.social.twitter.api.TwitterProfile;
import org.twitterobserver.model.TwitterProfileFixture;
import org.twitterobserver.model.entity.AuthorEntity;

public class AuthorEntityMapperTest {

	@Test
	public void shouldMapAllField_whenToEntity() throws Exception {
		TwitterProfile profile = TwitterProfileFixture.regular();

		AuthorEntity author = new AuthorMapper().toEntity(profile);

		assertThat(author.getUserId()).isEqualTo(profile.getId());
		assertThat(author.getCreationDate()).isEqualTo(profile.getCreatedDate().toInstant().toEpochMilli());
		assertThat(author.getName()).isEqualTo(profile.getName());
		assertThat(author.getScreenName()).isEqualTo(profile.getScreenName());
	}

}