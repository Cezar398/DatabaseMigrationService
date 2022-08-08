package com.assist.internship.migrationservice.api.v1.migration;

import com.assist.internship.migrationservice.api.v1.migration.dto.MigrationDto;
import com.assist.internship.migrationservice.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping()
    public ResponseEntity<?> create(MigrationDto migration) {
        String response = movieService.create(migration);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping()
    public Object getAll() {

        List<Movie> getMovies = movieService.getAll();

        if (getMovies.isEmpty()) {
            ResponseEntity<String> noContent = new ResponseEntity<>("No content", HttpStatus.NO_CONTENT);
            return noContent;
        }

        return movieService.getAll();
    }

    @GetMapping(path = "/{id}")
    public Object getById(@PathVariable("id") String id) {
        Optional<Movie> getMovie = movieService.getById(id);

        if (getMovie.equals(Optional.empty())) {
            ResponseEntity<String> noContent = new ResponseEntity<>("No Content", HttpStatus.NO_CONTENT);
            return noContent;
        }
        return getMovie;
    }

    @DeleteMapping()
    public Object deleteAll() {

        if (movieService.deleteAll() == true)
            return new ResponseEntity<>("OK", HttpStatus.OK);

        return new ResponseEntity<>("No content", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}")
    public Object deleteById(@PathVariable("id") String id) {


        if (movieService.deleteById(id) == true)
            return new ResponseEntity<>("OK", HttpStatus.OK);

        return new ResponseEntity<>("No content", HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "/{id}")
    public Object updateById(@PathVariable("id") String id, MigrationDto migrationDto) {

        Object updatedMovie = movieService.updateById(id, migrationDto);

        if(updatedMovie.equals(false))
            return new ResponseEntity<>("No content", HttpStatus.NO_CONTENT);

        return String.valueOf(updatedMovie);
    }
}
