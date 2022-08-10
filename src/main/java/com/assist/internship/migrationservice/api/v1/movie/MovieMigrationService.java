package com.assist.internship.migrationservice.api.v1.movie;

import com.assist.internship.migrationservice.entity.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieMigrationService {
    private final MovieService movieService;
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

        List<Movie> movies = new ArrayList<>();
        for (String id : idList) {
            String url = String.format("%s/find/%s?api_key=%s&language=en-US&external_source=imdb_id", baseUrl, id, token);
            Mono<LinkedHashMap> linkedHashMapMono = WebClient.create(url).get().retrieve().bodyToMono(LinkedHashMap.class);
            LinkedHashMap linkedHashMap = linkedHashMapMono.share().block();
            ArrayList movie_results = (ArrayList) linkedHashMap.get("movie_results");
            LinkedHashMap data = (LinkedHashMap) movie_results.get(0);
            movies.add(getMovie(data));
        }
        movieService.saveMovies(movies);
        log.info("Migration finished!");
    }

    private Movie getMovie(LinkedHashMap movieData) {
        Movie movie = new Movie();
        movie.setId(String.valueOf(movieData.get("id")));
        movie.setTitle(String.valueOf(movieData.get("title")));
        movie.setOverview(String.valueOf(movieData.get("overview")));
        movie.setPosterPath(String.valueOf(movieData.get("poster_path")));
        movie.setMediaType(String.valueOf(movieData.get("media_type")));
        movie.setPopularity(String.valueOf(movieData.get("popularity")));
        movie.setReleaseDate(String.valueOf(movieData.get("release_date")));
        movie.setVideo((boolean)movieData.get("video"));
        movie.setVoteAverage((float)((double)movieData.get("vote_average")));
        movie.setVoteCount((int) movieData.get("vote_count"));

        return movie;
    }

    private List<String> getIdList() {
        WebClient webClientObj = WebClient.builder().baseUrl("https://imdblistids.herokuapp.com/api/v1/movie/imdb/ids").filter(basicAuthentication(migrationUsername, migrationPassword)).build();
        return (List<String>) webClientObj.get().uri("").retrieve().bodyToMono(List.class).block();
    }
}
