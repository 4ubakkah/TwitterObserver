package org.twitterobserver.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.twitterobserver.model.entity.TweetEntity;

@Repository
public interface TweetRepository extends JpaRepository<TweetEntity, Long> {

	TweetEntity findFirst1ByOrderByCreationDateAsc();

	TweetEntity findFirst1ByOrderByCreationDateDesc();
}
