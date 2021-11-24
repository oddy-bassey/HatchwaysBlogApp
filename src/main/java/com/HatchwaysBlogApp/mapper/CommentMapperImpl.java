package com.HatchwaysBlogApp.mapper;

import com.HatchwaysBlogApp.dto.CommentRequest;
import com.HatchwaysBlogApp.dto.CommentResponse;
import com.HatchwaysBlogApp.model.Comment;
import com.HatchwaysBlogApp.model.Post;
import com.HatchwaysBlogApp.model.User;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.springframework.stereotype.Component;

@Component
public class CommentMapperImpl implements CommentMapper{

    @Override
    public Comment mapToModel(CommentRequest commentRequest, Post post, User user) {

        return Comment.builder()
                .text(commentRequest.getText())
                .post(post)
                .user(user)
                .build();
    }

    @Override
    public CommentResponse mapToDto(Comment comment) {

        return CommentResponse.builder()
                .id(comment.getId())
                .text(comment.getText())
                .postId(comment.getPost().getPostId())
                .username(comment.getUser().getUsername())
                .duration(getDuration(comment))
                .build();
    }

    @Override
    public String getDuration(Comment comment) {
        return TimeAgo.using(comment.getCreatedDate().toEpochMilli());
    }
}
