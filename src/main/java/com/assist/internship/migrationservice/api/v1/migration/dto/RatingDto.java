package com.assist.internship.migrationservice.api.v1.migration.dto;

import com.assist.internship.migrationservice.entity.Movie;
import lombok.Data;

@Data
public class RatingDto {
    private String content;
    private Integer rate;
    private Movie movie;
}
