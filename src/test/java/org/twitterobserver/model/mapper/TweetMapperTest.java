package org.twitterobserver.model.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.social.twitter.api.Tweet;
import org.twitterobserver.model.TweetFixture;
import org.twitterobserver.model.entity.AuthorEntity;
import org.twitterobserver.model.entity.AuthorFixture;
import org.twitterobserver.model.entity.TweetEntity;

@RunWith(MockitoJUnitRunner.class)
public class TweetMapperTest {

	@InjectMocks
	private TweetMapper tweetMapper;

	@Mock
	private AuthorMapper authorMapper;

	@Test
	public void shouldMappAllField_whenToEntity() throws Exception {
		Tweet tweet = TweetFixture.regular();
		AuthorEntity author = AuthorFixture.anAuthor();
		when(authorMapper.toEntity(tweet.getUser())).thenReturn(author);

		TweetEntity tweetEntity = tweetMapper.toEntity(tweet);

		assertThat(tweetEntity.getCreationDate()).isEqualTo(tweet.getCreatedAt().toInstant().toEpochMilli());
		assertThat(tweetEntity.getMessageContent()).isEqualTo(tweet.getText());
		assertThat(tweetEntity.getMessageId()).isEqualTo(tweet.getIdStr());
		assertThat(tweetEntity.getAuthor()).isEqualTo(author);
	}

}