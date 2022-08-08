package com.assist.internship.migrationservice.api.v1.migration;

import com.assist.internship.migrationservice.api.v1.migration.dto.MigrationDto;
import com.assist.internship.migrationservice.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private static final String NO_CONTENT_HTTP_RESPONSE = "No content";
    private static final String OK_HTTP_RESPONSE = "OK";

    @PostMapping()
    public Optional<Movie> create(MigrationDto migration) {
        return movieService.create(migration);
    }

    @GetMapping()
    public List<Movie> getAll() {
        return movieService.getAll();
    }

    @GetMapping(path = "/{id}")
    public Optional<Movie> getById(@PathVariable("id") String id) {
        Optional<Movie> movie = movieService.getById(id);

        if (movie.equals(Optional.empty())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
        return movie;
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteAll() {

        if (movieService.deleteAll()) return new ResponseEntity<String>(OK_HTTP_RESPONSE, HttpStatus.OK);

        return new ResponseEntity<String>(NO_CONTENT_HTTP_RESPONSE, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteById(@PathVariable("id") String id) {

        if (!movieService.deleteById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found!");
    }

    @PutMapping(path = "/{id}")
    public Optional<Movie> updateById(@PathVariable("id") String id, MigrationDto migrationDto) {

        Optional<Movie> updatedMovie = movieService.updateById(id, migrationDto);
        return updatedMovie;
    }
}
