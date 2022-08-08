package com.assist.internship.migrationservice.api.v1.movie;

import com.assist.internship.migrationservice.api.v1.movie.dto.MigrationDto;
import com.assist.internship.migrationservice.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping()
    public Optional<Movie> create(@RequestBody MigrationDto migration) {
        return movieService.create(migration);
    }

    @GetMapping()
    public List<Movie> getAll() {
        return movieService.getAll();
    }

    @GetMapping(path = "/{id}")
    public Movie getById(@PathVariable("id") String id) {
        return movieService.getById(id);
    }

    @DeleteMapping()
    public void deleteAll() {
        movieService.deleteAll();
    }

    @DeleteMapping(path = "/{id}")
    public void deleteById(@PathVariable("id") String id) {
        movieService.deleteById(id);
    }

    @PutMapping(path = "/{id}")
    public Movie updateById(@PathVariable("id") String id, MigrationDto migrationDto) {
        return movieService.updateById(id, migrationDto);
    }
}
