package org.twitterobserver.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tweet")
public class TweetEntity implements Comparable<TweetEntity> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String messageId;

	private long creationDate;

	private String messageContent;

	@ManyToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private AuthorEntity author;

	@Override
	public int compareTo(TweetEntity other) {
		return Long.compare(this.creationDate, other.creationDate);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("id", this.id)
				.append("messageId", this.messageId)
				.append("creationDate", this.creationDate)
				.append("messageContent", this.messageContent)
				.append("authorId", this.author.getId())
				.build();
	}
}
