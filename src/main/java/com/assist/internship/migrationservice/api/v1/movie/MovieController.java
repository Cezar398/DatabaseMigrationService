package com.assist.internship.migrationservice.api.v1.movie;

import com.assist.internship.migrationservice.api.v1.movie.dto.MovieDto;
import com.assist.internship.migrationservice.api.v1.movie.dto.MovieInfoDto;
import com.assist.internship.migrationservice.api.v1.movie.specification.MovieSearchCriteria;
import com.assist.internship.migrationservice.entity.Movie;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/api/v1/movie")
@RequiredArgsConstructor
@Tag(description = "Movies resources that provides access to available movies", name = "Movie Resource")
public class MovieController {
    private final MovieService movieService;
    private final MovieMigrationService movieMigrationService;

    @Operation(summary = "Add movie", description = "Add movie to the database and return it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie added successfully"),
            @ApiResponse(responseCode = "400", description = "Movie already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping()
    public Movie create(@Parameter(description = "Data required for create a movie") @RequestBody MovieDto movieDto) {
        return movieService.create(movieDto);
    }

    @Operation(summary = "Get movies", description = "Get all movies from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All movies have been selected"),
            @ApiResponse(responseCode = "404", description = "No movies found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })

    @GetMapping()
    public List<MovieInfoDto> getAll(@ParameterObject MovieSearchCriteria criteria) {
        MovieSearchCriteria movieSearchCriteria = MovieSearchCriteria.builder()
                .title(criteria.getTitle())
                .overview(criteria.getOverview())
                .voteCount(criteria.getVoteCount())
                .voteAverage(criteria.getVoteAverage())
                .build();
        return movieService.getAll(movieSearchCriteria);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie found"),
            @ApiResponse(responseCode = "404", description = "Movie not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @Operation(summary = "Get mvoie", description = "Get movie by id from database")
    @GetMapping(path = "/{id}")
    public Movie getById(@Parameter(description = "The id for movie which will be selected") @PathVariable("id") Long id) {
        return movieService.findById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie found"),
            @ApiResponse(responseCode = "404", description = "Movie not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @Operation(summary = "Delete movies", description = "Delete all movies from database")
    @DeleteMapping()
    public void deleteAll() {
        movieService.deleteAll();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie found"),
            @ApiResponse(responseCode = "404", description = "Movie not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @Operation(summary = "Delete movie", description = "Delete movie by id from database")
    @DeleteMapping(path = "/{id}")
    public void deleteById(@Parameter(description = "The id for movie which will be deleted") @PathVariable("id") Long id) {
        movieService.deleteById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie found"),
            @ApiResponse(responseCode = "404", description = "Movie not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @Operation(summary = "Update movie", description = "Update a movie from database")
    @PutMapping(path = "/{id}")
    public Movie updateById(@Parameter(description = "The id for movie which will be updated") @PathVariable("id") Long id, MovieDto movieDto) {
        return movieService.updateById(id, movieDto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie found"),
            @ApiResponse(responseCode = "404", description = "Movie not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @Operation(summary = "Start migration service", description = "We can start migration service by pressing \"Execute\". After press, migration will start. Now, movies from imdb will be migrated to our database")
    @PostMapping(path = "/migrate")
    public void migrateMovies() {
        movieMigrationService.migrateMovies();
    }

    @Operation(summary = "Export csv", description = "Export movie database to .csv file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movies found"),
            @ApiResponse(responseCode = "404", description = "Movies not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @GetMapping(value = "/export")
    public ResponseEntity<byte[]> exportToCSV() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + Movie.class.getSimpleName())
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(movieService.exportToCSV());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Import successful"),
            @ApiResponse(responseCode = "404", description = "Movies not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
    })
    @Operation(summary = "Import data from CSV file", description = "Import data from CSV file")
    @RequestMapping(value = "/import", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void importFromCSV(@Parameter(description = "CSV File for upload") @ModelAttribute MultipartFile file) {
        movieService.importFromCSV(file);
    }

    @Operation(summary = "Get failed migrations", description = "Get failed migrations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movies found"),
            @ApiResponse(responseCode = "404", description = "Movies not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @GetMapping(value = "/failed")
    public List<String> failedMovies() {
        return movieMigrationService.getFailedMovies();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movies migrated"),
            @ApiResponse(responseCode = "404", description = "Movies not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @Operation(summary = "Resume migrations", description = "Resume migrations")
    @PostMapping(value = "/resume")
    public void resumeMigration() {
        movieMigrationService.resumeMigration();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movies found"),
            @ApiResponse(responseCode = "404", description = "Movies not found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @Operation(summary = "Get last 10", description = "Get last 10 movies from database")
    @GetMapping("/last10/")
    public List<Movie> getLastTen() {
        return movieService.findLast10();
    }
}
