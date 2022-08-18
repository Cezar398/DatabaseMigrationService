package com.assist.internship.migrationService.api.v1.movie;

import com.assist.internship.migrationservice.api.v1.common.WebClientService;
import com.assist.internship.migrationservice.api.v1.movie.MovieMigrationService;
import com.assist.internship.migrationservice.api.v1.movie.MovieService;
import com.assist.internship.migrationservice.config.properties.MigrationConfig;
import com.assist.internship.migrationservice.entity.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieMigrationServiceTest {
    @InjectMocks
    private MovieMigrationService movieMigrationService;

    @Mock
    private MovieService movieService;
    @Mock
    private MigrationConfig migrationConfig;
    @Mock
    private WebClientService webClientService;

    @Test
    public void failedMovies_shouldReturnFailedMigrationMovies() {
        List<String> idList = new ArrayList<>();
        idList.add("id_1");
        idList.add("id_2");
        when(webClientService.getIds(anyString()))
                .thenReturn(idList);
        when(movieService.findAll())
                .thenReturn(List.of(getMovie()));

        List<String> result = movieMigrationService.failedMovies();
        assertEquals(1, result.size());
        assertTrue(result.contains("id_2"));
    }

    @Test
    public void failedMovies_shouldReturnNoFailedMigrationMovies() {
        when(webClientService.getIds(anyString()))
                .thenReturn(List.of());
        when(movieService.findAll())
                .thenReturn(List.of(getMovie()));

        List<String> result = movieMigrationService.failedMovies();
        assertEquals(0, result.size());
    }

    private static Movie getMovie() {
        Movie movie = new Movie();
        movie.setTitle("Title");
        movie.setOverview("Overview");
        movie.setVideo(true);
        movie.setPopularity("Popular");
        movie.setPosterPath("/poster_path.jpg");
        movie.setMediaType("MP4");
        movie.setVoteAverage(0.3F);
        movie.setVoteCount(100);
        movie.setReleaseDate("20-03-2000");
        movie.setId(1L);
        movie.setExternalId("id_1");

        return movie;
    }
}
