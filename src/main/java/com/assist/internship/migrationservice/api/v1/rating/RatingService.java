package com.assist.internship.migrationservice.api.v1.rating;

import com.assist.internship.migrationservice.api.v1.movie.MovieService;
import com.assist.internship.migrationservice.api.v1.rating.dto.RatingDto;
import com.assist.internship.migrationservice.entity.Movie;
import com.assist.internship.migrationservice.entity.Rating;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final MovieService movieService;

    public List<Rating> getAll() {
        return ratingRepository.findAll();
    }

    public Rating getById(Long id) {
        return ratingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Rating not found!"));
    }

    public Movie getRatingMoviesById(Long id) {
        return getById(id).getMovie();
    }

    public Rating createRating(RatingDto ratingDto) {
        Rating newRating = mapToRating(ratingDto);
        ratingRepository.save(newRating);

        return newRating;
    }

    private Rating mapToRating(RatingDto ratingDto) {
        Rating rating = new Rating();
        Movie movie = movieService.findById(ratingDto.getMovieId());
        rating.setRate(ratingDto.getRate());
        rating.setRateContent(ratingDto.getContent());
        rating.setMovie(movie);

        return rating;
    }
}
