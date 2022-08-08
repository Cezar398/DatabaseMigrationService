package com.assist.internship.migrationservice.api.v1.migration;

import com.assist.internship.migrationservice.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, String> {
}
