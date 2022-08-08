package com.assist.internship.migrationservice.api.v1.movie;

import com.assist.internship.migrationservice.api.v1.movie.dto.MigrationDto;
import com.assist.internship.migrationservice.entity.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public Optional<Movie> create(MigrationDto dtoMigration) {
        Movie localMovie = constructMovieData(dtoMigration);
        movieRepository.save(localMovie);

        return Optional.of(localMovie);
    }

    public List<Movie> getAll() {
        return movieRepository.findAll();
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

    public Movie updateById(String id, MigrationDto migrationDto) {
        Movie oldMovie = movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie not found"));
        Movie updatedMovie = constructMovieData(migrationDto);
        updatedMovie.setId(oldMovie.getId());

        return movieRepository.save(updatedMovie);

    }

    private Movie constructMovieData(MigrationDto dtoMigration) {
        Movie localMovie = new Movie();
        localMovie.setMediaType(dtoMigration.getMediaType());
        localMovie.setOverview(dtoMigration.getOverview());
        localMovie.setPopularity(dtoMigration.getPopularity());
        localMovie.setPosterPath(dtoMigration.getPosterPath());
        localMovie.setReleaseDate(dtoMigration.getReleaseDate());
        localMovie.setVideo(dtoMigration.getVideo());
        localMovie.setVoteAverage(dtoMigration.getVoteAverage());
        localMovie.setVoteCount(dtoMigration.getVoteCount());
        localMovie.setTitle(dtoMigration.getTitle());
        return localMovie;
    }
}
