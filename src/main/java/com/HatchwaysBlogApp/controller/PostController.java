package com.HatchwaysBlogApp.controller;

import com.HatchwaysBlogApp.dto.PostRequest;
import com.HatchwaysBlogApp.dto.PostResponse;
import com.HatchwaysBlogApp.dto.UpdatePostRequest;
import com.HatchwaysBlogApp.service.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostServiceImpl postService;

    @PostMapping("/")
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {

        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/edit-post/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable Long postId, @Valid @RequestBody UpdatePostRequest updatePostRequest) {

        postService.update(postId, updatePostRequest);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long postId) {
        return status(HttpStatus.OK).body(postService.getPost(postId));
    }

    @GetMapping("/by-user/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String name) {
        return status(HttpStatus.OK).body(postService.getPostsByUsername(name));
    }

    /*
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/
}
