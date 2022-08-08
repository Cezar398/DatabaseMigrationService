package com.assist.internship.migrationservice.api.v1.migration;

import com.assist.internship.migrationservice.api.v1.migration.dto.MigrationDto;
import com.assist.internship.migrationservice.entity.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public Optional<Movie> getById(String id) {
        return movieRepository.findById(id);
    }

    public boolean deleteAll() {
        List<Movie> movieList = movieRepository.findAll();

        if (!movieList.isEmpty()) {
            movieRepository.deleteAll();
            return true;
        }
        return false;
    }

    public boolean deleteById(String id) {
        Optional<Movie> movie = movieRepository.findById(id);

        if (movie.isPresent()) {
            movieRepository.deleteById(id);
            return true;
        }

        return false;
    }

    public Optional<Movie> updateById(String id, MigrationDto migrationDto) {
        Movie updatedMovie = constructMovieData(migrationDto);

        if (movieRepository.findById(id).isPresent()) {
            Movie oldMovie = movieRepository.getReferenceById(id);
            updatedMovie.setId(oldMovie.getId());
            movieRepository.save(updatedMovie);
            return Optional.of(updatedMovie);
        }

        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
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
