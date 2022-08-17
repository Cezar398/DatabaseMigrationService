package com.assist.internship.migrationservice.api.v1.movie;

import com.assist.internship.migrationservice.entity.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {MovieController.class})
@ExtendWith(SpringExtension.class)
class MovieControllerTest {
    //TODO: Refactor unit tests - method naming, split logic, etc.
    @Autowired
    private MovieController movieController;

    @MockBean
    private MovieMigrationService movieMigrationService;

    @MockBean
    private MovieService movieService;

    /**
     * Method under test: {@link MovieController#exportToCSV(HttpServletResponse)}
     */
    @Test
    void testExportToCSV() throws Exception {
        when(movieService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/movie/export");
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(movieController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/csv"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(
                String.join("", "id,title,overview,vote_count,vote_average,", System.getProperty("jdk.debug"), "_date\r\n")));
    }

    /**
     * Method under test: {@link MovieController#exportToCSV(HttpServletResponse)}
     */
    @Test
    void testExportToCSV2() throws Exception {
        Movie movie = new Movie();
        movie.setCountries(new ArrayList<>());
        movie.setExternalId("?");
        movie.setId(42l);
        movie.setMediaType("?");
        movie.setOverview("?");
        movie.setPopularity("?");
        movie.setPosterPath("?");
        movie.setRatings(new ArrayList<>());
        movie.setReleaseDate("2020-03-01");
        movie.setTitle("Dr");
        movie.setVideo(true);
        movie.setVoteAverage(10.0f);
        movie.setVoteCount(3);

        ArrayList<Movie> movieList = new ArrayList<>();
        movieList.add(movie);
        when(movieService.findAll()).thenReturn(movieList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/movie/export");
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(movieController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/csv"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(String.join("", "id,title,overview,vote_count,vote_average,",
                System.getProperty("jdk.debug"), "_date\r\n42,Dr,?,3,10.0,2020-03-01\r\n")));
    }

    /**
     * Method under test: {@link MovieController#exportToCSV(HttpServletResponse)}
     */
    @Test
    void testExportToCSV3() throws Exception {
        Movie movie = new Movie();
        movie.setCountries(new ArrayList<>());
        movie.setExternalId("?");
        movie.setId(42l);
        movie.setMediaType("?");
        movie.setOverview("?");
        movie.setPopularity("?");
        movie.setPosterPath("?");
        movie.setRatings(new ArrayList<>());
        movie.setReleaseDate("2020-03-01");
        movie.setTitle("Dr");
        movie.setVideo(true);
        movie.setVoteAverage(10.0f);
        movie.setVoteCount(3);

        Movie movie1 = new Movie();
        movie1.setCountries(new ArrayList<>());
        movie1.setExternalId("?");
        movie1.setId(42l);
        movie1.setMediaType("?");
        movie1.setOverview("?");
        movie1.setPopularity("?");
        movie1.setPosterPath("?");
        movie1.setRatings(new ArrayList<>());
        movie1.setReleaseDate("2020-03-01");
        movie1.setTitle("Dr");
        movie1.setVideo(true);
        movie1.setVoteAverage(10.0f);
        movie1.setVoteCount(3);

        ArrayList<Movie> movieList = new ArrayList<>();
        movieList.add(movie1);
        movieList.add(movie);
        when(movieService.findAll()).thenReturn(movieList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/movie/export");
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(movieController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/csv"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(String.join("", "id,title,overview,vote_count,vote_average,",
                System.getProperty("jdk.debug"), "_date\r\n42,Dr,?,3,10.0,2020-03-01\r\n42,Dr,?,3,10.0,2020-03-01\r\n")));
    }
}

