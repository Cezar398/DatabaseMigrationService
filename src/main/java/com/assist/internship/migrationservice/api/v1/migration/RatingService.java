package com.assist.internship.migrationservice.api.v1.migration;

import com.assist.internship.migrationservice.api.v1.migration.dto.RatingDto;
import com.assist.internship.migrationservice.entity.Rating;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RatingService {
    RatingRepository ratingRepository;

    public void addRating(RatingDto ratingDto)
    {
        Rating rating = new Rating();

        rating.setRate(ratingDto.getRate());
        rating.setMovie(ratingDto.getMovie());
        rating.setContent(ratingDto.getContent());

        ratingRepository.save(rating);
    }
}
