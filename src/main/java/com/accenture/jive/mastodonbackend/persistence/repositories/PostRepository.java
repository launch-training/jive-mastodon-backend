package com.accenture.jive.mastodonbackend.persistence.repositories;

import com.accenture.jive.mastodonbackend.persistence.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {



}
