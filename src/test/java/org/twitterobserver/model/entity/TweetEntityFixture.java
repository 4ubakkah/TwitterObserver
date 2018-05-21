package org.twitterobserver.model.entity;

import java.time.Instant;
import java.util.Date;

import com.github.javafaker.Faker;

public class TweetEntityFixture {

	private static final Faker faker = new Faker();

	public static TweetEntity withAuthor(AuthorEntity author) {
		return TweetEntity.builder()
				.messageId(faker.code().imei())
				.messageContent(faker.harryPotter().quote())
				.creationDate(Instant.now().toEpochMilli())
				.author(author)
				.build();
	}
}
