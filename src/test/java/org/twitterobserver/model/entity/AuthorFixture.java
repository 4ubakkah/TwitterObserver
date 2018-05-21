package org.twitterobserver.model.entity;

import java.util.Date;
import java.util.TreeSet;

import com.github.javafaker.Faker;

public class AuthorFixture {

	private static final Faker faker = new Faker();

	public static AuthorEntity anAuthor() {
		Date creationDate = faker.date().birthday();

		return AuthorEntity.builder()
				.userId(faker.number().randomNumber())
				.screenName(faker.name().username())
				.name(faker.name().firstName())
				.creationDate(creationDate.toInstant().toEpochMilli())
				.tweets(new TreeSet<>())
				.build();
	}


}
