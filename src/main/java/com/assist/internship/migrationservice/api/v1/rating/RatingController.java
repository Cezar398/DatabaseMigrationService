package com.assist.internship.migrationservice.api.v1.rating;

import com.assist.internship.migrationservice.api.v1.rating.dto.RatingDto;
import com.assist.internship.migrationservice.entity.Rating;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/rating")
@AllArgsConstructor
@Tag(description = "Rating resources that provides access to available ratings", name = "Rating Resource")
public class RatingController {

    private final RatingService ratingService;

    @Operation(summary = "Get ratings", description = "Get all rating from database")
    @GetMapping
    public List<Rating> getAll()
    {
        return ratingService.getAll();
    }

    @Operation(summary = "Add rating", description = "Create rating for a moie")
    @PostMapping
    public Rating createRating(@Parameter(description = "Rating data, id -> Movie id, rateContent -> What rating contains, rate -> Rate score") @RequestBody RatingDto ratingDto)
    {
       return ratingService.createRating(ratingDto);
    }
}
