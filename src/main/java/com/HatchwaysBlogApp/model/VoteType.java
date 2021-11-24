package com.HatchwaysBlogApp.model;

import com.HatchwaysBlogApp.exceptions.BlogAppException;

import java.util.Arrays;

public enum VoteType {
    DOWNVOTE(-1), UPVOTE(1), ;

    private int direction;

    VoteType(int direction) {}

    public static VoteType lookup(Integer direction) {
        return Arrays.stream(VoteType.values())
                .filter(value -> value.getDirection().equals(direction))
                .findAny()
                .orElseThrow(() -> new BlogAppException("Vote not found"));
    }

    public Integer getDirection() {
        return direction;
    }
}
