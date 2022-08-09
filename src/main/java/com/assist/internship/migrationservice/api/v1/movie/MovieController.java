package com.assist.internship.migrationservice.api.v1.movie;

import com.assist.internship.migrationservice.api.v1.movie.dto.MovieDto;
import com.assist.internship.migrationservice.entity.Movie;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private final MovieMigrationService movieMigrationService;

    @PostMapping()
    public Movie create(@Parameter(description = "Data required for create a movie") @RequestBody MovieDto movieDto) {
        return movieService.create(movieDto);
    }

    @GetMapping()
    public List<Movie> getAll() {
        return movieService.getAll();
    }

    @GetMapping(path = "/{id}")
    public Movie getById(@Parameter(description = "The id for movie which will be selected") @PathVariable("id") String id) {
        return movieService.getById(id);
    }

    @DeleteMapping()
    public void deleteAll() {
        movieService.deleteAll();
    }

    @DeleteMapping(path = "/{id}")
    public void deleteById(@Parameter(description = "The id for movie which will be deleted") @PathVariable("id") String id) {
        movieService.deleteById(id);
    }

    @PutMapping(path = "/{id}")
    public Movie updateById(@Parameter(description = "The id for movie which will be updated") @PathVariable("id") String id, MovieDto movieDto) {
        return movieService.updateById(id, movieDto);
    }

    @PostMapping(path = "/migrate")
    public void migrateMovies() throws JSONException {
       movieMigrationService.migrateMovies();
    }

    @GetMapping(path = "/vote/greater/{vote_count}")
    public List<Movie> getMovieByVoteCountGreaterThan(@PathVariable("vote_count") int voteCount)
    {
        return movieService.getMovieByVoteCountGreaterThan(voteCount);
    }

    @GetMapping(path = "/vote/less/{vote_count}")
    public List<Movie> getMoviesByVoteCountIsLessThan(@PathVariable("vote_count") int voteCount)
    {
        return movieService.getMoviesByVoteCountIsLessThan(voteCount);
    }

    @GetMapping(path = "/vote/lesseq/{vote_count}")
    public List<Movie> getMoviesByVoteCountIsLessThanEqual(@PathVariable("vote_count") int voteCount)
    {
        return movieService.getMoviesByVoteCountIsLessThanEqual(voteCount);
    }

    @GetMapping(path = "/vote/greatereq/{vote_count}")
    public List<Movie> getMoviesByVoteCountGreaterThanEqual(@PathVariable("vote_count") int voteCount)
    {
        return movieService.getMoviesByVoteCountGreaterThanEqual(voteCount);
    }

    @GetMapping(path = "/overview/contains/{text}")
    public List<Movie> getMoviesByOverviewContains(@PathVariable("text") String s)
    {
        return movieService.getMoviesByOverviewContains(s);
    }

    @GetMapping(path = "/overview/notcontains/{text}")
    public List<Movie> getMoviesByOverviewIsNotContaining(@PathVariable("text") String s)
    {
        return movieService.getMoviesByOverviewIsNotContaining(s);
    }



}
