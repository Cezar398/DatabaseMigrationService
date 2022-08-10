package com.assist.internship.migrationservice.api.v1.movie;

import com.assist.internship.migrationservice.api.v1.movie.dto.MovieObject;
import com.assist.internship.migrationservice.api.v1.movie.dto.MovieResponse;
import com.assist.internship.migrationservice.entity.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieMigrationService {
    private final MovieService movieService;
    //TODO: Use config properties
    @Value("${custom.migration.baseUrl}")
    private String baseUrl;
    @Value("${custom.migration.token}")
    private String token;
    @Value("${custom.migration.username}")
    private String migrationUsername;
    @Value("${custom.migration.password}")
    private String migrationPassword;

    public void migrateMovies() {
        log.info("Start migration!");
        List<String> idList = getIdList();
        if (idList.isEmpty()) {
            return;
        }
        List<Movie> movies = new ArrayList<>();
        idList.stream()
                .parallel()
                .forEach(id -> {
                    log.info("Get info for movie with id:" + id);
                    //TODO: Use spring cache to impove migration
                    String url = String.format("%s/find/%s?api_key=%s&language=en-US&external_source=imdb_id", baseUrl, id, token);
                    //TODO: Create beans for WebClient
                    MovieResponse movieResponse = WebClient.create(url).get().retrieve().bodyToMono(MovieResponse.class).block();
                    movies.add(getMovie(movieResponse.getFirst()));
                });
        movieService.saveMovies(movies);
        log.info("Migration finished!");
    }

    private Movie getMovie(MovieObject movieData) {
        Movie movie = new Movie();
        movie.setExternal_id(String.valueOf(movieData.getId()));
        movie.setTitle(movieData.getTitle());
        movie.setOverview(movieData.getOverview());
        movie.setPosterPath(movieData.getPoster_path());
        movie.setMediaType(movieData.getMedia_type());
        movie.setPopularity(String.valueOf(movieData.getPopularity()));
        movie.setReleaseDate(movieData.getRelease_date());
        movie.setVideo(movieData.getVideo());
        movie.setVoteAverage(movieData.getVote_average());
        movie.setVoteCount(movieData.getVote_count());

        return movie;
    }

    private List<String> getIdList() {
        WebClient webClientObj = WebClient.builder().baseUrl("https://imdblistids.herokuapp.com/api/v1/movie/imdb/ids").filter(basicAuthentication(migrationUsername, migrationPassword)).build();
        return (List<String>) webClientObj.get().uri("").retrieve().bodyToMono(List.class).block();
    }
}
