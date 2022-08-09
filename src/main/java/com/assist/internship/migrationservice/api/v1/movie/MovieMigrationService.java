package com.assist.internship.migrationservice.api.v1.movie;

import com.assist.internship.migrationservice.entity.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
    @Value("${custom.migration.baseUrl}")
    private String baseUrl;
    @Value("${custom.migration.token}")
    private String token;
    @Value("${custom.migration.username}")
    private String migrationUsername;
    @Value("${custom.migration.password}")
    private String migrationPassword;

    public void migrateMovies() throws JSONException {
        log.info("Start migration!");
        List<String> idList = getIdList();

        List<Movie> movies = new ArrayList<>();
        for (String id : idList) {
            String url = String.format("%s/find/%s?api_key=%s&language=en-US&external_source=imdb_id", baseUrl, id, token);
            WebClient webClientObj = WebClient.builder().baseUrl(url).build();
            JSONObject jsonObject = new JSONObject(webClientObj.get().uri("").retrieve().bodyToMono(String.class).block());
            JSONArray jsonArray = jsonObject.getJSONArray("movie_results");
            JSONObject movieJson = jsonArray.getJSONObject(0);

            movies.add(getMovie(movieJson));

        }
        movieService.saveMovies(movies);
        log.info("Migration finished!");
    }

    private Movie getMovie(JSONObject movieData) throws JSONException {
        Movie movie = new Movie();
        movie.setId(movieData.getString("id"));
        movie.setTitle(movieData.getString("title"));
        movie.setOverview(movieData.getString("overview"));
        movie.setPosterPath(movieData.getString("poster_path"));
        movie.setMediaType(movieData.getString("media_type"));
        movie.setPopularity(movieData.getString("popularity"));
        movie.setReleaseDate(movieData.getString("release_date"));
        movie.setVideo(movieData.getBoolean("video"));
        movie.setVoteAverage((float) movieData.getDouble("vote_average"));
        movie.setVoteCount(movieData.getInt("vote_count"));

        return movie;
    }

    private List<String> getIdList() {
        WebClient webClientObj = WebClient.builder().baseUrl("https://imdblistids.herokuapp.com/api/v1/movie/imdb/ids").filter(basicAuthentication(migrationUsername, migrationPassword)).build();
        return (List<String>) webClientObj.get().uri("").retrieve().bodyToMono(List.class).block();
    }
}
