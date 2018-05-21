package org.twitterobserver.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.twitterobserver.model.entity.AuthorEntity;
import org.twitterobserver.service.TestDataGenerator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorEntityRepositoryTest {

	@Autowired
	private AuthorRepository authorRepository;

	@Test
	public void shouldFindAllAuthorsSortedByCreationDateAsc_whenFindAllByOrderByCreationDateAsc() throws Exception {
		Set<AuthorEntity> authors = TestDataGenerator.composeTestData();
		authorRepository.saveAll(authors);

		try {
			//Ugly chunk of code but makes sure generated test data is not sorted initially
			//assertThat(nonSortedTweets).flatExtracting(TweetEntity::getCreationDate).isSorted();
			List<AuthorEntity> allAuthors = authorRepository.findAll();
			assertThat(allAuthors).isSorted();
			allAuthors.forEach(a -> assertThat(a.getTweets().stream().collect(Collectors.toList())).isSorted());
			failBecauseExceptionWasNotThrown(AssertionError.class);
		} catch (AssertionError e) {
			//Safely ignore exception as it is expected
		}

		List<AuthorEntity> allAuthorsSorted = authorRepository.findAllByOrderByCreationDateAsc();

		assertThat(allAuthorsSorted).isSorted();
		allAuthorsSorted.forEach(a -> assertThat(a.getTweets().stream().collect(Collectors.toList())).isSorted());
	}

}