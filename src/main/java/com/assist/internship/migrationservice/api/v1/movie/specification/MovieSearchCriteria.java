package com.assist.internship.migrationservice.api.v1.movie.specification;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieSearchCriteria {
    @Parameter(in = ParameterIn.QUERY,description = "Movie title")
    private String title;
    @Parameter(in = ParameterIn.QUERY,description = "Vote count")
    private Integer voteCount;
    @Parameter(in = ParameterIn.QUERY,description = "Voate average")
    private Float voteAverage;
    @Parameter(in = ParameterIn.QUERY,description = "Overview")
    private String overview;
}
