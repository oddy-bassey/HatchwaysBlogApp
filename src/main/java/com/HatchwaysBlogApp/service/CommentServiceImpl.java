package com.HatchwaysBlogApp.service;

import com.HatchwaysBlogApp.dto.CommentRequest;
import com.HatchwaysBlogApp.dto.CommentResponse;
import com.HatchwaysBlogApp.exceptions.PostNotFoundException;
import com.HatchwaysBlogApp.mapper.CommentMapper;
import com.HatchwaysBlogApp.model.Comment;
import com.HatchwaysBlogApp.model.Post;
import com.HatchwaysBlogApp.model.User;
import com.HatchwaysBlogApp.repository.CommentRepository;
import com.HatchwaysBlogApp.repository.PostRepository;
import com.HatchwaysBlogApp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    @Override
    public void save(CommentRequest commentRequest) {

        Post post = postRepository.findById(commentRequest.getPostId())
                .orElseThrow(() -> new PostNotFoundException(String.format("post with id %s not found!", commentRequest.getPostId().toString())));

        Comment comment = commentMapper.mapToModel(commentRequest, post, authService.getCurrentUser());
        comment.setCreatedDate(Instant.now());
        comment.setLastUpdated(Instant.now());

        commentRepository.save(comment);

        //send mail notification to post owner
    }

    @Override
    public List<CommentResponse> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(toList());
    }

    @Override
    public List<CommentResponse> getAllCommentsForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("no user exists with username %s", username)));

        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }
}
