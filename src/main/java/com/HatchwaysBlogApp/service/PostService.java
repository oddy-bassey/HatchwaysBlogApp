package com.HatchwaysBlogApp.service;

import com.HatchwaysBlogApp.dto.PostRequest;
import com.HatchwaysBlogApp.dto.PostResponse;
import com.HatchwaysBlogApp.dto.UpdatePostRequest;

import java.util.List;

public interface PostService {

    void save(PostRequest postRequest);

    void update(Long postId, UpdatePostRequest updatePostRequest);

    PostResponse getPost(Long id);

    List<PostResponse> getAllPosts();

    List<PostResponse> getPostsByUsername(String username);
}
