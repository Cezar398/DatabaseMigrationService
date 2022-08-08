package com.assist.internship.migrationservice.api.v1.rating;

import com.assist.internship.migrationservice.api.v1.rating.dto.RatingDto;
import com.assist.internship.migrationservice.entity.Rating;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public Optional<Rating> createRating(@RequestBody RatingDto ratingDto)
    {
       return ratingService.createRating(ratingDto);
    }
}
