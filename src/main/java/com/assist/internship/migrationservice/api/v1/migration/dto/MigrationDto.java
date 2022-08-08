package com.assist.internship.migrationservice.api.v1.migration.dto;

import lombok.Data;

@Data
public class MigrationDto {
    private String title;
    private String overview;
    private String poster_path;
    private String media_type;
    private String popularity;
    private String release_date;
    private Boolean video;
    private Float vote_average;
    private Integer vote_count;
    }
