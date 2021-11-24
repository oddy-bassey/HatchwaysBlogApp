package com.HatchwaysBlogApp.mapper;

import com.HatchwaysBlogApp.dto.PostRequest;
import com.HatchwaysBlogApp.dto.PostResponse;
import com.HatchwaysBlogApp.model.Post;
import com.HatchwaysBlogApp.model.User;
import com.HatchwaysBlogApp.model.VoteType;

public interface PostMapper {

    Post mapToModel(PostRequest postRequest, User user);

    PostResponse mapToDto(Post post);

    Integer voteCount(Post post);

    Integer commentCount(Post post);

    String getDuration(Post post);

    boolean isPostUpVoted(Post post);

    boolean isPostDownVoted(Post post);

    boolean checkVoteType(Post post, VoteType voteType);
}