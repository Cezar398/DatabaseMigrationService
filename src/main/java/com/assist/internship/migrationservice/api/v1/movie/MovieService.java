package com.assist.internship.migrationservice.api.v1.movie;

import com.assist.internship.migrationservice.api.v1.movie.dto.MovieDto;
import com.assist.internship.migrationservice.api.v1.movie.specification.MovieSpecification;
import com.assist.internship.migrationservice.api.v1.movie.specification.MovieSearchCriteria;
import com.assist.internship.migrationservice.entity.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public Movie create(MovieDto movieDto) {
        Movie movie = mapToMovie(movieDto);
        movieRepository.save(movie);

        return movie;
    }

    public List<Movie> getAll(MovieSearchCriteria movieSearchCriteria) {
        MovieSpecification specification = new MovieSpecification(movieSearchCriteria);
        return movieRepository.findAll(specification);
    }

    public Movie getById(String id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found!"));
    }

    public void deleteAll() {
        movieRepository.deleteAll();
    }

    public void deleteById(String id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found!"));

        movieRepository.delete(movie);
    }

    public Movie updateById(String id, MovieDto movieDto) {
        Movie oldMovie = movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found"));
        Movie updatedMovie = mapToMovie(movieDto);
        updatedMovie.setId(oldMovie.getId());

        return movieRepository.save(updatedMovie);

    }

    public void saveMovies(List<Movie> movies) {
        movieRepository.saveAll(movies);
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
