package com.HatchwaysBlogApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostRequest {

    @NotEmpty(message = "title is required")
    private String title;

    @NotEmpty(message = "url is required")
    private String url;

    @NotEmpty(message = "description is required")
    private String description; 
}
