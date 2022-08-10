package com.assist.internship.migrationservice.api.v1.movie.dto;

import lombok.Data;

import java.util.List;

@Data
public class MovieResponse {
    private List<MovieObject> movie_results;

    public MovieObject getFirst() {
        return movie_results.get(0);
    }
}
