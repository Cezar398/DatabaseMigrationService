package com.assist.internship.migrationservice.api.v1.rating;

import com.assist.internship.migrationservice.api.v1.movie.MovieService;
import com.assist.internship.migrationservice.api.v1.rating.dto.RatingDto;
import com.assist.internship.migrationservice.entity.Movie;
import com.assist.internship.migrationservice.entity.Rating;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {
    @Mock
    private MovieService movieService;

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingService ratingService;

    @Test
    void getAll_shouldReturnRatings() {
        when(ratingRepository.findAll()).thenReturn(List.of(getRatingMock()));

        List<Rating> result = ratingService.getAll();
        assertFalse(result.isEmpty());
    }

    @Test
    void createRating_shouldSucceed() {
        when(ratingRepository.save(any())).thenReturn(getRatingMock());
        Movie mockMovie = getMovieMock();
        when(movieService.findById(anyString())).thenReturn(mockMovie);
        RatingDto ratingDto = getRatingDtoMock();

        Rating result = ratingService.createRating(ratingDto);
        assertEquals("Not all who wander are lost", result.getRateContent());
        assertEquals(1, result.getRate());
        assertSame(mockMovie, result.getMovie());
        verify(ratingRepository).save(any());
        ArgumentCaptor<String> findByIdArg = ArgumentCaptor.forClass(String.class);
        verify(movieService, times(1)).findById(findByIdArg.capture());
        assertEquals(ratingDto.getMovieId(), findByIdArg.getValue());
    }

    private static RatingDto getRatingDtoMock() {
        RatingDto ratingDto = new RatingDto();
        ratingDto.setContent("Not all who wander are lost");
        ratingDto.setMovieId("42");
        ratingDto.setRate(1);

        return ratingDto;
    }

    private static Movie getMovieMock() {
        Movie movie1 = new Movie();
        movie1.setCountries(new ArrayList<>());
        movie1.setExternalId("External id");
        movie1.setId(42L);
        movie1.setMediaType("Media Type");
        movie1.setOverview("Overview");
        movie1.setPopularity("Popularity");
        movie1.setPosterPath("Poster Path");
        movie1.setRatings(new ArrayList<>());
        movie1.setReleaseDate("2020-03-01");
        movie1.setTitle("Dr");
        movie1.setVideo(true);
        movie1.setVoteAverage(10.0f);
        movie1.setVoteCount(3);

        return movie1;
    }

    private static Rating getRatingMock() {
        Movie movie = getMovieMock();
        Rating rating = new Rating();
        rating.setId(42L);
        rating.setMovie(movie);
        rating.setRate(1);
        rating.setRateContent("Not all who wander are lost");

        return rating;
    }
}

