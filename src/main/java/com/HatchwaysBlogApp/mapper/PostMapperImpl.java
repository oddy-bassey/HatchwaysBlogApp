package com.HatchwaysBlogApp.mapper;

import com.HatchwaysBlogApp.dto.PostRequest;
import com.HatchwaysBlogApp.dto.PostResponse;
import com.HatchwaysBlogApp.model.Post;
import com.HatchwaysBlogApp.model.User;
import com.HatchwaysBlogApp.model.Vote;
import com.HatchwaysBlogApp.model.VoteType;
import com.HatchwaysBlogApp.repository.CommentRepository;
import com.HatchwaysBlogApp.repository.VoteRepository;
import com.HatchwaysBlogApp.service.AuthenticationService;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.HatchwaysBlogApp.model.VoteType.DOWNVOTE;
import static com.HatchwaysBlogApp.model.VoteType.UPVOTE;

@RequiredArgsConstructor
@Component
public class PostMapperImpl implements PostMapper{

    private final CommentRepository commentRepository;
    private final VoteRepository voteRepository;
    private final AuthenticationService authService;

    @Override
    public Post mapToModel(PostRequest postRequest, User user) {

        return Post.builder()
                .postId(postRequest.getPostId())
                .title(postRequest.getTitle())
                .description(postRequest.getDescription())
                .url(postRequest.getUrl())
                .user(user)
                .voteCount(0)
                .createdDate(postRequest.getCreatedDate())
                .lastUpdated(postRequest.getLastUpdated())
                .build();
    }

    @Override
    public PostResponse mapToDto(Post post) {

        return PostResponse.builder()
                .id(post.getPostId())
                .title(post.getTitle())
                .url(post.getUrl())
                .description(post.getDescription())
                .username(post.getUser().getUsername())
                .voteCount(voteCount(post))
                .commentCount(commentCount(post))
                .duration(getDuration(post))
                .upVote(isPostUpVoted(post))
                .downVote(isPostDownVoted(post))
                .build();
    }

    @Override
    public Integer voteCount(Post post) {
        return voteRepository.findByPost(post).size();
    }

    @Override
    public Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    @Override
    public String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

    @Override
    public boolean isPostUpVoted(Post post) {
        return checkVoteType(post, UPVOTE);
    }

    @Override
    public boolean isPostDownVoted(Post post) {
        return checkVoteType(post, DOWNVOTE);
    }

    @Override
    public boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser =
                    voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                            authService.getCurrentUser());
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }
}
