package com.assist.internship.migrationservice.api.v1.movie.dto;

import lombok.Data;

import java.util.List;

@Data
public class MovieObject {
    private Boolean adult;
    private String backgrop_path;
    private Integer id;
    private String title;
    private String original_language;
    private String original_title;
    private String overview;
    private String poster_path;
    private String media_type;
    private List<Integer> genre_ids;
    private Float popularity;
    private String release_date;
    private Boolean video;
    private Float vote_average;
    private Integer vote_count;
}
