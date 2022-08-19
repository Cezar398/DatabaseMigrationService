package com.assist.internship.migrationservice.api.v1.movie;

import com.assist.internship.migrationservice.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    @Query(value = "SELECT * FROM Movie ORDER BY release_date DESC LIMIT 10", nativeQuery = true)
    List<Movie> findLast10();
}
