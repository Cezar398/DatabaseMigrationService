package com.assist.internship.migrationservice.api.v1.movie;

import com.assist.internship.migrationservice.api.v1.movie.dto.MovieDto;
import com.assist.internship.migrationservice.api.v1.movie.specification.MovieSearchCriteria;
import com.assist.internship.migrationservice.entity.Movie;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/movie")
@RequiredArgsConstructor
@Tag(description = "Movies resources that provides access to available movies", name = "Movie Resource")
public class MovieController {

    private final MovieService movieService;
    private final MovieMigrationService movieMigrationService;


    @Operation(summary = "Add movie", description = "Add movie to the database")
    @PostMapping()
    public Movie create(@Parameter(description = "Data required for create a movie") @RequestBody MovieDto movieDto) {
        return movieService.create(movieDto);
    }

    @Operation(summary = "Get movies", description = "Get all movies from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "All movies have been selected")
    })
    @GetMapping()
    public List<Movie> getAll(@RequestParam(value = "title", required = false) String title) {
        MovieSearchCriteria movieSearchCriteria = MovieSearchCriteria.builder()
                .title(title)
                .build();

        return movieService.getAll(movieSearchCriteria);
    }

    @Operation(summary = "Get mvoie", description = "Get movie by id from database")
    @GetMapping(path = "/{id}")
    public Movie getById(@Parameter(description = "The id for movie which will be selected") @PathVariable("id") String id) {
        return movieService.getById(id);
    }

    @Operation(summary = "Delete movies", description = "Delete all movies from database")
    @DeleteMapping()
    public void deleteAll() {
        movieService.deleteAll();
    }

    @Operation(summary = "Delete movie", description = "Delete movie by id from database")
    @DeleteMapping(path = "/{id}")
    public void deleteById(@Parameter(description = "The id for movie which will be deleted") @PathVariable("id") String id) {
        movieService.deleteById(id);
    }

    @Operation(summary = "Update movie", description = "Update a movie from database")
    @PutMapping(path = "/{id}")
    public Movie updateById(@Parameter(description = "The id for movie which will be updated") @PathVariable("id") String id, MovieDto movieDto) {
        return movieService.updateById(id, movieDto);
    }

    @Operation(summary = "Start migration service", description = "We can start migration service by pressing \"Execute\". After press, migration will start. Now, movies from imdb will be migrated to our database")
    @PostMapping(path = "/migrate")
    public void migrateMovies() {
        movieMigrationService.migrateMovies();
    }
}
