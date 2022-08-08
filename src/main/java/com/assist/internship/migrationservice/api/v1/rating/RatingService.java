package com.assist.internship.migrationservice.api.v1.rating;

import com.assist.internship.migrationservice.api.v1.movie.MovieService;
import com.assist.internship.migrationservice.api.v1.rating.dto.RatingDto;
import com.assist.internship.migrationservice.entity.Movie;
import com.assist.internship.migrationservice.entity.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final MovieService movieService;

    public List<Rating> getAll()
    {
        return ratingRepository.findAll();
    }

    public Optional<Rating> createRating(RatingDto ratingDto)
    {
        Rating newRating = mapToRating(ratingDto);

        ratingRepository.save(newRating);

        return Optional.of(newRating);
    }

    private Rating mapToRating(RatingDto ratingDto)
    {
        Rating rating = new Rating();
        Movie movie = movieService.getById(ratingDto.getMovieId());

        rating.setRate(ratingDto.getRate());
        rating.setRateContent(ratingDto.getContent());
        rating.setMovie(movie);

        return rating;
    }
}
