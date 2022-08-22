package com.assist.internship.migrationservice.api.v1.movie;

import com.assist.internship.migrationservice.api.v1.common.CSVHelper;
import com.assist.internship.migrationservice.api.v1.movie.dto.MovieDto;
import com.assist.internship.migrationservice.api.v1.movie.specification.MovieSearchCriteria;
import com.assist.internship.migrationservice.api.v1.movie.specification.MovieSpecification;
import com.assist.internship.migrationservice.config.properties.ExportConfig;
import com.assist.internship.migrationservice.config.properties.ImportConfig;
import com.assist.internship.migrationservice.entity.Movie;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final ExportConfig exportConfig;
    private final ImportConfig importConifg;
    @Autowired
    private HttpServletRequest httpServletRequest;

    public Movie create(MovieDto movieDto) {
        Movie movie = mapToMovie(movieDto);
        movieRepository.save(movie);

        return movie;
    }

    public List<Movie> getAll(MovieSearchCriteria movieSearchCriteria) {
        MovieSpecification specification = new MovieSpecification(movieSearchCriteria);
        return movieRepository.findAll(specification);
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Movie findById(Long id) {
        return movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Movie not found!"));
    }

    public void deleteAll() {
        movieRepository.deleteAll();
    }

    public void deleteById(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Movie not found!"));

        movieRepository.delete(movie);
    }

    public Movie updateById(Long id, MovieDto movieDto) {
        Movie oldMovie = movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Movie not found"));
        Movie updatedMovie = mapToMovie(movieDto);
        updatedMovie.setId(oldMovie.getId());

        return movieRepository.save(updatedMovie);

    }

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public void saveMovies(List<Movie> movies) {
        movieRepository.saveAll(movies);
    }

    @SneakyThrows
    public void exportToCSV(HttpServletResponse response) {
        response.setContentType(exportConfig.getContentType());
        response.setHeader(exportConfig.getHeaderKey(), exportConfig.getHeaderValue() + Movie.class.getName() + exportConfig.getFileFormat());

        CSVHelper csvHelper = new CSVHelper();
        csvHelper.setCsvFormat(CSVFormat.EXCEL);
        csvHelper.setPrintWriter(response.getWriter());
        List<Movie> movies = findAll();
        csvHelper.writeToCsv(movies, Movie.class);

    }

    public List<Movie> findLast10() {
        return movieRepository.findLast10();
    }

    private Movie mapToMovie(MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setMediaType(movieDto.getMediaType());
        movie.setOverview(movieDto.getOverview());
        movie.setPopularity(movieDto.getPopularity());
        movie.setPosterPath(movieDto.getPosterPath());
        movie.setReleaseDate(movieDto.getReleaseDate());
        movie.setVideo(movieDto.getVideo());
        movie.setVoteAverage(movieDto.getVoteAverage());
        movie.setVoteCount(movieDto.getVoteCount());
        movie.setTitle(movieDto.getTitle());
        return movie;
    }
}
