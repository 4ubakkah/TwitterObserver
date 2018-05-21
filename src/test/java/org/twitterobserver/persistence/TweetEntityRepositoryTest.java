package org.twitterobserver.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.twitterobserver.model.entity.AuthorEntity;
import org.twitterobserver.model.entity.TweetEntity;
import org.twitterobserver.service.TestDataGenerator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TweetEntityRepositoryTest {

	@Autowired
	private TweetRepository tweetRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Test
	public void shouldFindFirstTweet_whenFindFirst1ByOrderByCreationDateAsc() throws Exception {
		Set<AuthorEntity> authors = TestDataGenerator.composeTestData();
		authorRepository.saveAll(authors);

		TweetEntity first = tweetRepository.findFirst1ByOrderByCreationDateAsc();

		List<TweetEntity> all = tweetRepository.findAll();
		assertThat(all.get(0).getCreationDate()).isEqualTo(first.getCreationDate());
	}

	@Test
	public void shouldFindLastTweet_whenFindFirst1ByOrderByCreationDateAsc() throws Exception {
		Set<AuthorEntity> authors = TestDataGenerator.composeTestData();
		authorRepository.saveAll(authors);

		TweetEntity last = tweetRepository.findFirst1ByOrderByCreationDateDesc();

		List<TweetEntity> all = tweetRepository.findAll();
		assertThat(all.get(all.size() - 1).getCreationDate()).isEqualTo(last.getCreationDate());
	}
}