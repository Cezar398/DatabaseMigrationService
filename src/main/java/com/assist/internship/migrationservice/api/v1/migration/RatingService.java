package com.assist.internship.migrationservice.api.v1.migration;

import com.assist.internship.migrationservice.api.v1.migration.dto.RatingDto;
import com.assist.internship.migrationservice.entity.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;

    public List<Rating> getAll()
    {
        return ratingRepository.findAll();
    }

    public Optional<Rating> createRating(RatingDto ratingDto)
    {
        Rating newRating = constructRating(ratingDto);

        ratingRepository.save(newRating);

        return Optional.of(newRating);
    }

    private Rating constructRating(RatingDto ratingDto)
    {
        Rating localRating = new Rating();

        localRating.setRate(ratingDto.getRate());
        localRating.setRateContent(ratingDto.getContent());

        return localRating;
    }
}
