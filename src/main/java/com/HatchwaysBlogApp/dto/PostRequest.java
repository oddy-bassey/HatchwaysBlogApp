package com.HatchwaysBlogApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private Long postId;
    private String title;
    private String url;
    private String description;
    private Instant createdDate;
    private Instant lastUpdated;
}
