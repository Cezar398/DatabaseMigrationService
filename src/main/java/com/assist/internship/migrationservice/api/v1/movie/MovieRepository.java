package com.assist.internship.migrationservice.api.v1.movie;

import com.assist.internship.migrationservice.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String>, JpaSpecificationExecutor<Movie> {
    List<Movie> findMovieByExternalId(String externalId);

    @Query(value = "SELECT * FROM Movie ORDER BY release_date DESC", nativeQuery = true)
    Page<Movie> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM Movie", nativeQuery = true)
    Page<Movie> findAllPage(Pageable pageable);
}
