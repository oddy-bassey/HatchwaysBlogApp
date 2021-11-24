package com.HatchwaysBlogApp.service;

import com.HatchwaysBlogApp.dto.VoteDto;
import com.HatchwaysBlogApp.exceptions.BlogAppException;
import com.HatchwaysBlogApp.exceptions.PostNotFoundException;
import com.HatchwaysBlogApp.model.Post;
import com.HatchwaysBlogApp.model.Vote;
import com.HatchwaysBlogApp.model.VoteType;
import com.HatchwaysBlogApp.repository.PostRepository;
import com.HatchwaysBlogApp.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteServiceImpl implements VoteService{

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthenticationService authService;

    @Transactional
    @Override
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));

        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())) {
            throw new BlogAppException("You have already "
                    + voteDto.getVoteType() + "'d for this post");
        }
        if (VoteType.UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
