package com.assist.internship.migrationservice.api.v1.migration;

import com.assist.internship.migrationservice.api.v1.migration.dto.MigrationDto;
import com.assist.internship.migrationservice.entity.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MigrationService {
    private final MigrationRepository migrationRepository;

    public void create(MigrationDto dtoMigration) {
        Movie localMovie = constructMovieData(dtoMigration);
        migrationRepository.save(localMovie);
    }

    public List<Movie> getAll() {
        List<Movie> list;
        list = migrationRepository.findAll();
        return list;
    }

    public Optional<Movie> getById(String id) {
        return migrationRepository.findById(id);
    }

    public void deleteAll() {
        migrationRepository.deleteAll();
    }

    public void deleteById(String id) {
        migrationRepository.deleteById(id);
    }

    public void updateById(String id, MigrationDto migrationDto) {
        Movie oldMovie = migrationRepository.getReferenceById(id);
        Movie updatedMovie = constructMovieData(migrationDto);
        updatedMovie.setId(oldMovie.getId());
        migrationRepository.save(updatedMovie);
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
