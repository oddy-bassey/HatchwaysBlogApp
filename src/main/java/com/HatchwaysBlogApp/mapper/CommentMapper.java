package com.HatchwaysBlogApp.mapper;

import com.HatchwaysBlogApp.dto.CommentRequest;
import com.HatchwaysBlogApp.dto.CommentResponse;
import com.HatchwaysBlogApp.model.Comment;
import com.HatchwaysBlogApp.model.Post;
import com.HatchwaysBlogApp.model.User;

public interface CommentMapper {

    Comment mapToModel(CommentRequest commentRequest, Post post, User user);

    CommentResponse mapToDto(Comment comment);

    String getDuration(Comment comment);
}