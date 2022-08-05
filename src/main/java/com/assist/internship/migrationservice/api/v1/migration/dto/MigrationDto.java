package com.assist.internship.migrationservice.api.v1.migration.dto;

import lombok.Data;

@Data
public class MigrationDto {
    private String title;
    private String overview;
    private String posterPath;
    private String mediaType;
    private String popularity;
    private String releaseDate;
    private Boolean video;
    private Float voteAverage;
    private Integer voteCount;
    }
