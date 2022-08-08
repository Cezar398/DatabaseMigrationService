package com.assist.internship.migrationservice.api.v1.migration;

import com.assist.internship.migrationservice.api.v1.migration.dto.RatingDto;
import com.assist.internship.migrationservice.entity.Rating;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/rating")
@AllArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @GetMapping
    public List<Rating> getAll()
    {
        return ratingService.getAll();
    }

    @PostMapping
    public Optional<Rating> createRating(RatingDto ratingDto)
    {
       return ratingService.createRating(ratingDto);
    }
}
