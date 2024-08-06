package com.accenture.jive.mastodonbackend.controllers;

import com.accenture.jive.mastodonbackend.controllers.dtos.PostDtoOutput;
import com.accenture.jive.mastodonbackend.controllers.mappers.PostMapper;
import com.accenture.jive.mastodonbackend.persistence.entities.Post;
import com.accenture.jive.mastodonbackend.persistence.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;
    @Autowired
    PostMapper postMapper;

    @GetMapping("/posts")
    public ResponseEntity<List<PostDtoOutput>> readAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostDtoOutput> dtos = postMapper.toDtos(posts);
        return ResponseEntity.ok(dtos);
    }

}
