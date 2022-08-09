package com.assist.internship.migrationservice.api.v1.movie;

import com.assist.internship.migrationservice.api.v1.movie.dto.MovieDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieMigrationService {
    private final MovieService movieService;

    public String getIdList(String username, String password) {
        String url = "https://imdblistids.herokuapp.com/api/v1/movie/imdb/ids";
        String authStr = username + ":" + password;
        String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);

        return response.getBody();
    }

    public void migrateMovies(String username, String password) throws JSONException {
        String idList = getIdList(username, password);
        String token = "a7f7650bc6053c146e113d011a993b07";
        JSONArray json = new JSONArray(idList);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity request = new HttpEntity(headers);

        String url;

        for (int i = 0; i < json.length(); i++) {
            url = "https://api.themoviedb.org/3/find/" + json.getString(i) + "?api_key=" + token + "&language=en-US&external_source=imdb_id";
            ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);
            JSONObject jsonObject = new JSONObject(response.getBody());
            JSONArray jsonArray = jsonObject.getJSONArray("movie_results");
            JSONObject movieData = jsonArray.getJSONObject(0);
            MovieDto movieDto = new MovieDto();

            try {
                movieDto.setTitle(movieData.getString("title"));
                movieDto.setOverview(movieData.getString("overview"));
                movieDto.setPosterPath(movieData.getString("poster_path"));
                movieDto.setMediaType(movieData.getString("media_type"));
                movieDto.setPopularity(movieData.getString("popularity"));
                movieDto.setReleaseDate(movieData.getString("release_date"));
                movieDto.setVideo(movieData.getBoolean("video"));
                movieDto.setVoteAverage((float) movieData.getDouble("vote_average"));
                movieDto.setVoteCount(movieData.getInt("vote_count"));
                movieService.create(movieDto);
            } catch (Exception e) {
                log.info(String.valueOf(e));
            }
        }
    }
}
