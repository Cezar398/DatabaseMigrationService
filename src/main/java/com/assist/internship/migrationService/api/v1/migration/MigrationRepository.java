package com.assist.internship.migrationService.api.v1.migration;

import com.assist.internship.migrationService.entity.Migration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MigrationRepository extends JpaRepository<Migration, String> {
}
