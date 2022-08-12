package com.assist.internship.migrationservice.api.v1.movie;

import com.assist.internship.migrationservice.api.v1.common.WebClientService;
import com.assist.internship.migrationservice.api.v1.movie.dto.MovieObject;
import com.assist.internship.migrationservice.api.v1.movie.dto.MovieResponse;
import com.assist.internship.migrationservice.config.properties.MigrationConfig;
import com.assist.internship.migrationservice.entity.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieMigrationService {
    private final MovieService movieService;
    private final MigrationConfig migrationConfig;
    private final WebClientService webClientService;

    @Cacheable(value = "cacheMigratedMovies")
    public void migrateMovies(List<String> idList) {

        log.info("Start migration!");
        if (idList.isEmpty()) {
            return;
        }
        List<Movie> movies = new ArrayList<>();

        idList.stream().parallel().forEach(id -> {
            log.info("Get info for movie with id:" + id);
            String url = String.format("/find/%s?api_key=%s&language=en-US&external_source=imdb_id", id, migrationConfig.getToken());
            MovieResponse movieResponse = webClientService.getMovie(url);
            movies.add(getMovie(movieResponse.getFirst(), id));
        });
        movieService.saveMovies(movies);
        log.info("Migration finished!");
    }

    public void resumeMigration() {
        migrateMovies(failedMovies());
    }

    private Movie getMovie(MovieObject movieData, String id) {
        Movie movie = new Movie();
        movie.setExternal_id(id);
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

    public List<String> failedMovies() {
        int failNumber = 0;
        List<String> idList = getIdList();
        List<String> notFoundIdList = new ArrayList<>();
        List<Movie> movies = movieService.findAll();
        for (String id : idList) {
            Boolean isFound = false;
            for (Movie movie : movies) {
                if (movie.getExternal_id().equals(id)) {
                    isFound = true;
                    break;
                }
            }

            if (!isFound) {
                notFoundIdList.add(id);
            }
        }

        return notFoundIdList;
    }

    @Cacheable(value = "cacheIdList")
    public List<String> getIdList() {
        return webClientService.getIds("/api/v1/movie/imdb/ids");
    }
}
