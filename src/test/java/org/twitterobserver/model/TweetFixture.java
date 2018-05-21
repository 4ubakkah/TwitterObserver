package org.twitterobserver.model;

import com.github.javafaker.Faker;
import org.springframework.social.twitter.api.Tweet;

public class TweetFixture {

	private static final Faker faker = new Faker();

	public static Tweet regular() {
		return new Tweet(1L,
				faker.idNumber().valid(),
				faker.harryPotter().quote(),
				faker.date().birthday(),
				faker.name().username(),
				faker.internet().url(),
				1L,
				1L,
				"NL",
				faker.internet().domainName());
	}
}
