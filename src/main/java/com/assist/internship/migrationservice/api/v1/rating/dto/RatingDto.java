package com.assist.internship.migrationservice.api.v1.rating.dto;

import lombok.Data;

@Data
public class RatingDto {
    private String content;
    private Integer rate;
    private Long movieId;
}
