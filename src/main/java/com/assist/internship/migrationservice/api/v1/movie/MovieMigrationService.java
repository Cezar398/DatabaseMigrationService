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
    public void migrateMovies() {
        List<String> idList = getIdList();
        if (idList.isEmpty()) {
            return;
        }
        List<Movie> movies = getMovies(idList);
        movieService.saveMovies(movies);
    }

    private List<Movie> getMovies(List<String> idList) {
        List<Movie> movies = new ArrayList<>();

        //TODO: improve parallel processing
        idList.stream().parallel().forEach(id -> {
            //TODO: extract to a separate method
            String url = String.format("/find/%s?api_key=%s&language=en-US&external_source=imdb_id", id, migrationConfig.getToken());
            MovieResponse movieResponse = webClientService.getMovie(url);
            movies.add(getMovie(movieResponse.getFirst(), id));
        });
        return movies;
    }

    public void resumeMigration() {
        List<String> idList = failedMovies();
        if (idList.isEmpty()) {
            return;
        }
        List<Movie> movies = getMovies(idList);
        movieService.saveMovies(movies);
    }

    private Movie getMovie(MovieObject movieData, String id) {
        Movie movie = new Movie();
        movie.setExternalId(id);
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

    public List<String> getFailedMovies() {
        //TODO: remove this method
        return failedMovies();
    }

    private List<String> failedMovies() {
        List<String> idList = getIdList();
        List<String> notFoundIdList = new ArrayList<>();
        List<Movie> movies = movieService.findAll();
        //TODO: re-write the way you populate addedIds using Java 8 stream features
        List<String> addedIds = new ArrayList<>();

        movies.stream().forEach(movie -> {
            addedIds.add(movie.getExternalId());
        });

        idList.stream().forEach(id -> {
            if (!addedIds.contains(id)) {
                notFoundIdList.add(id);
            }
        });

        return notFoundIdList;
    }

    private List<String> getIdList() {
        return webClientService.getIds("/api/v1/movie/imdb/ids");
    }
}
