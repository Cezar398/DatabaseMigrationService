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
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

@RestController
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
    public List<Movie> getAll(@ParameterObject MovieSearchCriteria criteria) {
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
    public Movie getById(@Parameter(description = "The id for movie which will be selected") @PathVariable("id") String id) {
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
    public void deleteById(@Parameter(description = "The id for movie which will be deleted") @PathVariable("id") String id) {
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
    public Movie updateById(@Parameter(description = "The id for movie which will be updated") @PathVariable("id") String id, MovieDto movieDto) {
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
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        String currentDateTIme = dateFormat.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=\"movies_" + currentDateTIme + ".csv\"";
        response.setHeader(headerKey, headerValue);

        List<Movie> movieList = movieService.findAll();

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"id", "title", "overview", "vote_count", "vote_average", "release_date"};
        String[] nameMapping = {"id", "title", "overview", "voteCount", "voteAverage", "releaseDate"};
        csvWriter.writeHeader(csvHeader);
        for (Movie movie : movieList) {
            csvWriter.write(movie, nameMapping);
        }
        csvWriter.close();
    }
}
