package com.assist.internship.migrationservice.api.v1.movie.specification;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MovieSearchCriteria {
    private String title;
    private String voteCount;
    private String voteAverage;
    private String overview;
}
