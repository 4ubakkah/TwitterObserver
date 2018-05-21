package org.twitterobserver.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.twitterobserver.model.entity.AuthorEntity;

@Repository
public interface AuthorRepository extends JpaRepository <AuthorEntity, Long> {

	List<AuthorEntity> findAllByOrderByCreationDateAsc();
}
