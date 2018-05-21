package org.twitterobserver.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.SortedSet;
import java.util.TreeSet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Builder
@Data
@EqualsAndHashCode(exclude = {"tweets"})
@AllArgsConstructor
@Entity
@Table(name = "author")
public class AuthorEntity implements Comparable<AuthorEntity> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private long userId;
	private long creationDate;
	private String name;
	private String screenName;
	@OrderBy("creation_date ASC")
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private SortedSet<TweetEntity> tweets;

	public AuthorEntity() {
		tweets = new TreeSet<>();
	}

	@Override
	public int compareTo(AuthorEntity o) {
		return Long.compare(this.creationDate, o.creationDate);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("id", this.id)
				.append("userId", this.userId)
				.append("creationDate", this.creationDate)
				.append("name", this.name)
				.append("screenName", this.screenName)
				.append("tweets", this.tweets)
				.build();
	}
}
