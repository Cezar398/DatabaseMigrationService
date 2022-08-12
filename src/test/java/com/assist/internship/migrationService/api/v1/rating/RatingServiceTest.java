package com.assist.internship.migrationservice.api.v1.rating;

import com.assist.internship.migrationservice.api.v1.movie.MovieService;
import com.assist.internship.migrationservice.api.v1.rating.dto.RatingDto;
import com.assist.internship.migrationservice.entity.Movie;
import com.assist.internship.migrationservice.entity.Rating;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {RatingService.class})
@ExtendWith(SpringExtension.class)
class RatingServiceTest {
    @MockBean
    private MovieService movieService;

    @MockBean
    private RatingRepository ratingRepository;

    @Autowired
    private RatingService ratingService;

    /**
     * Method under test: {@link RatingService#getAll()}
     */
    @Test
    void testGetAll() {
        ArrayList<Rating> ratingList = new ArrayList<>();
        when(ratingRepository.findAll()).thenReturn(ratingList);
        List<Rating> actualAll = ratingService.getAll();
        assertSame(ratingList, actualAll);
        assertTrue(actualAll.isEmpty());
        verify(ratingRepository).findAll();
    }

    /**
     * Method under test: {@link RatingService#createRating(RatingDto)}
     */
    @Test
    void testCreateRating() {
        Movie movie = new Movie();
        movie.setCountries(new ArrayList<>());
        movie.setExternal_id("External id");
        movie.setId("42");
        movie.setMediaType("Media Type");
        movie.setOverview("Overview");
        movie.setPopularity("Popularity");
        movie.setPosterPath("Poster Path");
        movie.setRatings(new ArrayList<>());
        movie.setReleaseDate("2020-03-01");
        movie.setTitle("Dr");
        movie.setVideo(true);
        movie.setVoteAverage(10.0f);
        movie.setVoteCount(3);

        Rating rating = new Rating();
        rating.setId("42");
        rating.setMovie(movie);
        rating.setRate(1);
        rating.setRateContent("Not all who wander are lost");
        when(ratingRepository.save((Rating) any())).thenReturn(rating);

        Movie movie1 = new Movie();
        movie1.setCountries(new ArrayList<>());
        movie1.setExternal_id("External id");
        movie1.setId("42");
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
        when(movieService.findById((String) any())).thenReturn(movie1);

        RatingDto ratingDto = new RatingDto();
        ratingDto.setContent("Not all who wander are lost");
        ratingDto.setMovieId("42");
        ratingDto.setRate(1);
        Rating actualCreateRatingResult = ratingService.createRating(ratingDto);
        assertEquals("Not all who wander are lost", actualCreateRatingResult.getRateContent());
        assertEquals(1, actualCreateRatingResult.getRate());
        assertSame(movie1, actualCreateRatingResult.getMovie());
        verify(ratingRepository).save((Rating) any());
        verify(movieService).findById((String) any());
    }
}

