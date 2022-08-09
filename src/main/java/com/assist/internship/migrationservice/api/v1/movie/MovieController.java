package com.assist.internship.migrationservice.api.v1.movie;

import com.assist.internship.migrationservice.api.v1.movie.dto.MigrationDto;
import com.assist.internship.migrationservice.api.v1.movie.dto.MovieDto;
import com.assist.internship.migrationservice.entity.Movie;
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
    public Movie create(@RequestBody MovieDto movieDto) {
        return movieService.create(movieDto);
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
    public Movie updateById(@PathVariable("id") String id, MovieDto movieDto) {
        return movieService.updateById(id, movieDto);
    }
    
    @GetMapping(path = "/migrate/{username}/{password}")
    public String getIDs(@PathVariable("username") String username, @PathVariable("password") String password){
       return movieMigrationService.getIdList(username, password);
    }

    @PostMapping(path = "/migrate")
    public void migrateMovies(@RequestBody MigrationDto migrationDto) throws JSONException {
        System.out.println(migrationDto.getUsername()+ " " + migrationDto.getPassword());
       movieMigrationService.migrateMovies(migrationDto.getUsername(), migrationDto.getPassword());
    }
}
