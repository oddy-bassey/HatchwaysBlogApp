package com.HatchwaysBlogApp.service;

import com.HatchwaysBlogApp.dto.PostRequest;
import com.HatchwaysBlogApp.dto.PostResponse;
import com.HatchwaysBlogApp.dto.UpdatePostRequest;
import com.HatchwaysBlogApp.exceptions.PostNotFoundException;
import com.HatchwaysBlogApp.mapper.PostMapper;
import com.HatchwaysBlogApp.model.Post;
import com.HatchwaysBlogApp.model.User;
import com.HatchwaysBlogApp.repository.CommentRepository;
import com.HatchwaysBlogApp.repository.PostRepository;
import com.HatchwaysBlogApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authService;
    private final PostMapper postMapper;

    @Override
    public void save(PostRequest postRequest) {

        Post post = postMapper.mapToModel(postRequest, authService.getCurrentUser());
        post.setCreatedDate(Instant.now());
        post.setLastUpdated(Instant.now());

        postRepository.save(post);
    }

    @Override
    public void update(Long postId, UpdatePostRequest updatePostRequest) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(String.format("no post exist with id: %s", postId.toString())));

        post.setTitle(updatePostRequest.getTitle());
        post.setDescription(updatePostRequest.getDescription());
        post.setUrl(updatePostRequest.getUrl());
        post.setLastUpdated(Instant.now());

        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    @Override
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(String.format("no posts exist with id: %s", id.toString())));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }


    @Transactional(readOnly = true)
    @Override
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("no user exists with username %s !", username)));

        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Override
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(String.format("no post exist with id: %s", postId.toString())));

        //post.setUser(null);

        //TO-DO
        //delete all comments associated to post if any
        //delete all votes associated to post if any

        postRepository.delete(post);
    }
}
