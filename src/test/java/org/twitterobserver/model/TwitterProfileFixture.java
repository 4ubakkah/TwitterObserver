package org.twitterobserver.model;

import com.github.javafaker.Faker;
import org.springframework.social.twitter.api.TwitterProfile;

public class TwitterProfileFixture {

	private static final Faker faker = new Faker();

	public static TwitterProfile regular() {
		TwitterProfile twitterProfile = new TwitterProfile(1L,
				faker.name().username(),
				faker.name().fullName(),
				faker.internet().url(),
				faker.internet().url(),
				faker.gameOfThrones().quote(),
				"NL",
				faker.date().birthday());

		return twitterProfile;
	}
}
