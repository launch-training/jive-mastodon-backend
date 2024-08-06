package com.accenture.jive.mastodonbackend.controllers;

import com.accenture.jive.mastodonbackend.controllers.dtos.PostDtoOutput;
import com.accenture.jive.mastodonbackend.persistence.entities.Post;
import com.accenture.jive.mastodonbackend.persistence.repositories.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostControllerTest {

    @Autowired
    PostController postController;
    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void cleanUpDB() {
        postRepository.deleteAll();
    }

    @Test
    void readAllPosts() {
        {
            ResponseEntity<List<PostDtoOutput>> result = postController.readAllPosts();
            HttpStatusCode actualStatusCode = result.getStatusCode();
            HttpStatusCode expectedStatusCode = HttpStatusCode.valueOf(200);
            assertEquals(expectedStatusCode, actualStatusCode);
            List<PostDtoOutput> resultBody = result.getBody();
            assertNotNull(resultBody);
            assertEquals(0, resultBody.size());
        }
        {
            Post post1 = createPost("https://mastodon.social/@jiive/112914298415386928");
            Post post2 = createPost("https://mastodon.social/@jiive/112913971809344034");
            ResponseEntity<List<PostDtoOutput>> result = postController.readAllPosts();
            HttpStatusCode actualStatusCode = result.getStatusCode();
            HttpStatusCode expectedStatusCode = HttpStatusCode.valueOf(200);
            assertEquals(expectedStatusCode, actualStatusCode);
            List<PostDtoOutput> resultBody = result.getBody();
            assertNotNull(resultBody);
            assertEquals(2, resultBody.size());
        }
    }

    private Post createPost(String postLink) {
        Post post = new Post();
        post.setGuid(UUID.randomUUID().toString());
        post.setTimestampWeatherRequest(LocalDateTime.now());
        post.setTimestampMastodonPosted(LocalDateTime.now());
        post.setPostLink(postLink);
        postRepository.save(post);
        return post;
    }

}
