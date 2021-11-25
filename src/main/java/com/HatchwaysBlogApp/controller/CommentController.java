package com.HatchwaysBlogApp.controller;

import com.HatchwaysBlogApp.dto.CommentRequest;
import com.HatchwaysBlogApp.dto.CommentResponse;
import com.HatchwaysBlogApp.service.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceImpl commentService;

    @PostMapping("/")
    public ResponseEntity<Void> createComment(@RequestBody CommentRequest commentRequest) {
        commentService.save(commentRequest);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsForPost(@PathVariable Long postId) {
        return ResponseEntity.status(OK)
                .body(commentService.getAllCommentsForPost(postId));
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsForUser(@PathVariable String username){
        return ResponseEntity.status(OK)
                .body(commentService.getAllCommentsForUser(username));
    }

}
