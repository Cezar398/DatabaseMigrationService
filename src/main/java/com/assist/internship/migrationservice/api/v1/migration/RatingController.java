package com.assist.internship.migrationservice.api.v1.migration;

import com.assist.internship.migrationservice.api.v1.migration.dto.RatingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/v1/rating")
@RestController
@Slf4j
@RequiredArgsConstructor
public class RatingController {

    RatingService ratingService;

    @PostMapping
    public void addRating(RatingDto ratingDto)
    {
        ratingService.addRating(ratingDto);
    }
}
