package com.HatchwaysBlogApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRequest {

    @NotEmpty(message = "title is required")
    private String title;

    @NotEmpty(message = "url is required")
    private String url;

    @NotEmpty(message = "description is required")
    private String description;
}
