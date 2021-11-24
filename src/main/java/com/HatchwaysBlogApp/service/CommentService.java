package com.HatchwaysBlogApp.service;

import com.HatchwaysBlogApp.dto.CommentRequest;
import com.HatchwaysBlogApp.dto.CommentResponse;

import java.util.List;

public interface CommentService {

    void save(CommentRequest commentRequest);

    List<CommentResponse> getAllCommentsForPost(Long postId);

    List<CommentResponse> getAllCommentsForUser(String userName);
}
