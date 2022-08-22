package com.assist.internship.migrationservice.api.v1.rating;

import com.assist.internship.migrationservice.api.v1.rating.dto.RatingDto;
import com.assist.internship.migrationservice.entity.Rating;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RatingController.class)
class RatingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    @Test
    void createRating_ShouldCreateRating() throws Exception {
        Rating rating = getRating();

        Mockito.when(ratingService.createRating(any())).thenReturn(rating);

        RatingDto ratingDto = getRatingDto();
        String content = (new ObjectMapper()).writeValueAsString(ratingDto);

        mockMvc.perform(post("/api/v1/rating")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());
    }

    @Test
    void getAll_ShouldReturnAllRatings() throws Exception {
        Rating rating = getRating();

        List<Rating> ratingList = Arrays.asList(rating);
        Mockito.when(ratingService.getAll()).thenReturn(ratingList);

        mockMvc.perform(get("/api/v1/rating"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].rateContent", Matchers.is("Rate content")))
                .andExpect(jsonPath("$[0].rate", Matchers.is(10)));
    }

    private static RatingDto getRatingDto() {
        RatingDto ratingDto = new RatingDto();
        ratingDto.setContent("Not all who wander are lost");
        ratingDto.setMovieId(42L);
        ratingDto.setRate(1);
        return ratingDto;
    }

    private static Rating getRating() {
        Rating rating = new Rating();
        rating.setId(1L);
        rating.setRateContent("Rate content");
        rating.setRate(10);
        return rating;
    }

}

