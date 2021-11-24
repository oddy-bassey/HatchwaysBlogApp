package com.HatchwaysBlogApp.service;

import com.HatchwaysBlogApp.dto.PostRequest;
import com.HatchwaysBlogApp.dto.PostResponse;
import com.HatchwaysBlogApp.exceptions.PostNotFoundException;
import com.HatchwaysBlogApp.mapper.PostMapper;
import com.HatchwaysBlogApp.model.Post;
import com.HatchwaysBlogApp.model.User;
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

    @Transactional(readOnly = true)
    @Override
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
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
}
