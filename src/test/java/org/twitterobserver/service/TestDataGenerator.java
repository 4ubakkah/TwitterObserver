package org.twitterobserver.service;

import java.util.LinkedHashSet;
import java.util.Set;

import org.twitterobserver.model.entity.AuthorEntity;
import org.twitterobserver.model.entity.AuthorFixture;
import org.twitterobserver.model.entity.TweetEntity;
import org.twitterobserver.model.entity.TweetEntityFixture;

public class TestDataGenerator {

	public static Set<AuthorEntity> composeTestData() {
		Set<TweetEntity> results = new LinkedHashSet<>();
		Set<AuthorEntity> authors = new LinkedHashSet<>();

		for (int i = 0; i < 999; i++) {
			AuthorEntity author = AuthorFixture.anAuthor();
			authors.add(author);
		}

		authors.forEach(a -> {
			for (int i = 0; i < 10; i++) {
				TweetEntity tweetEntity = TweetEntityFixture.withAuthor(a);
				a.getTweets().add(tweetEntity);
				results.add(tweetEntity);
			}
		});

		return authors;
	}
}