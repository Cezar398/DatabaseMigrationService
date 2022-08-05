package com.assist.internship.migrationservice.api.v1.migration;

import com.assist.internship.migrationservice.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MigrationRepository extends JpaRepository<Movie, String> {
}
