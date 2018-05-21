package org.twitterobserver.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.twitterobserver.model.entity.AuthorEntity;
import org.twitterobserver.model.entity.AuthorFixture;

@RunWith(MockitoJUnitRunner.class)
public class ProducerServiceTest {

	@InjectMocks
	private ProducerService producerService;

	@Mock
	private ObjectMapper objectMapper;

	@Test
	//TODO rework this one
	public void shouldMarshall_whenPrint() throws Exception {
		List<AuthorEntity> authors = Arrays.asList(AuthorFixture.anAuthor());

		producerService.print(authors);

		Mockito.verify(objectMapper).writeValueAsString(authors);
	}

	@Test
	public void shouldNotThrowException_whenPrint_givenSerializationExceptionHappened() throws Exception {
		List<AuthorEntity> authors = Arrays.asList(AuthorFixture.anAuthor());
		when(objectMapper.writeValueAsString(authors)).thenThrow(JsonParseException.class);

		assertThatCode(() -> producerService.print(authors)).doesNotThrowAnyException();
	}

}