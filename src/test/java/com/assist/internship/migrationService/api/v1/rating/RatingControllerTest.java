package com.assist.internship.migrationservice.api.v1.rating;

import com.assist.internship.migrationservice.api.v1.rating.dto.RatingDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = {RatingController.class})
@ExtendWith(SpringExtension.class)
class RatingControllerTest {
    @Autowired
    private RatingController ratingController;

    @MockBean
    private RatingService ratingService;

    /**
     * Method under test: {@link RatingController#createRating(RatingDto)}
     */
    @Test
    void testCreateRating() throws Exception {
        when(ratingService.getAll()).thenReturn(new ArrayList<>());

        RatingDto ratingDto = new RatingDto();
        ratingDto.setContent("Not all who wander are lost");
        ratingDto.setMovieId("42");
        ratingDto.setRate(1);
        String content = (new ObjectMapper()).writeValueAsString(ratingDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/rating")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(ratingController)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("[]"))
                .andExpect(jsonPath("$.rate").doesNotExist());
    }

    /**
     * Method under test: {@link RatingController#getAll()}
     */
    @Test
    void testGetAll() throws Exception {
        when(ratingService.getAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/rating");
        MockMvcBuilders.standaloneSetup(ratingController)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("[]"));
    }

    /**
     * Method under test: {@link RatingController#getAll()}
     */
    @Test
    void testGetAll2() throws Exception {
        when(ratingService.getAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/v1/rating");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(ratingController)
                .build()
                .perform(getResult)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string("[]"));
    }
}

