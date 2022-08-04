package com.assist.internship.migrationService.api.v1.migration;

import com.assist.internship.migrationService.api.v1.migration.dto.MigrationDto;
import com.assist.internship.migrationService.entity.Migration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MigrationService {

    @Autowired
    MigrationRepository migrationRepository;

    private Migration construct_movie(MigrationDto dtoMigration)
    {
        Migration local_movie = new Migration();
        local_movie.setMedia_type(dtoMigration.getMedia_type());
        local_movie.setOverview(dtoMigration.getOverview());
        local_movie.setPopularity(dtoMigration.getPopularity());
        local_movie.setPoster_path(dtoMigration.getPoster_path());
        local_movie.setRelease_date(dtoMigration.getRelease_date());
        local_movie.setVideo(dtoMigration.getVideo());
        local_movie.setVote_average(dtoMigration.getVote_average());
        local_movie.setVote_count(dtoMigration.getVote_count());
        local_movie.setTitle(dtoMigration.getTitle());

        return local_movie;
    }
    public void create(MigrationDto dtoMigration)
    {
        Migration local_movie;

        local_movie = construct_movie(dtoMigration);

        try {
            migrationRepository.save(local_movie);
        }catch (Exception e)
        {
            log.info("Database insert error: " + e);
        }

    }


    public List<Migration> getAll()
    {
        List<Migration> list = null;
        try
        {
            list = migrationRepository.findAll();
        }catch (Exception e)
        {
            log.info("Error in getting movies: " + e);
        }
        return list;
    }

    public Optional<Migration> getById(String id)
    {
        Optional<Migration> list = null;

        try{
            list = migrationRepository.findById(id);
        }catch (Exception e)
        {
            log.info("Error in getting data from this id: " + id);
        }

        return list;
    }

    public void deleteAll()
    {
        try{
            migrationRepository.deleteAll();
        }catch (Exception e)
        {
            log.info("Database delete all error: " + e);
        }
    }

    public void deleteById(String id) {
        try{
            migrationRepository.deleteById(id);
        }catch (Exception e)
        {
            log.info("Error in deleting movie: " + e);
        }
    }



    public void updateById(String id, MigrationDto migrationDto)
    {
        Migration migration = migrationRepository.getReferenceById(id);

        migration.setTitle(migrationDto.getTitle());
        migration.setOverview(migrationDto.getOverview());
        migration.setPoster_path(migrationDto.getPoster_path());
        migration.setMedia_type(migrationDto.getMedia_type());
        migration.setPopularity(migrationDto.getPopularity());
        migration.setRelease_date(migrationDto.getRelease_date());
        migration.setVideo(migrationDto.getVideo());
        migration.setVote_average(migrationDto.getVote_average());
        migration.setVote_count(migrationDto.getVote_count());


        try {
            migrationRepository.save(migration);
        }catch (Exception e)
        {
            log.info("Error in updating movie: " + e);
        }
    }


}
