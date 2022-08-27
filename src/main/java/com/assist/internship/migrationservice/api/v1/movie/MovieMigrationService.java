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

import java.util.List;
import java.util.stream.Collectors;

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
        movieService.saveMovies(getMovies(idList));
    }

    private List<Movie> getMovies(List<String> idList) {
        return idList.stream().parallel().map(this::getMovie).collect(Collectors.toList());
    }

    private Movie getMovie(String id) {
        String url = String.format("/find/%s?api_key=%s&language=en-US&external_source=imdb_id", id, migrationConfig.getToken());
        MovieResponse movieResponse = webClientService.getMovie(url);
        return getMovie(movieResponse.getFirst(), id);
    }

    public void resumeMigration() {
        List<String> idList = getFailedMovies();
        if (idList.isEmpty()) {
            return;
        }
        movieService.saveMovies(getMovies(idList));
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
        List<String> idList = getIdList();
        List<String> addedIds = movieService.findAll().stream().map(Movie::getExternalId).toList();
        return idList.stream().filter(id -> !addedIds.contains(id)).toList();
    }

    @Cacheable(value = "idList")
    public List<String> getIdList()
    {
        return IdList();
    }

    private List<String> IdList() {
        return webClientService.getIds("/api/v1/movie/imdb/ids");
    }

}
