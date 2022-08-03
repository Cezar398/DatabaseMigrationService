package com.assist.internship.migrationService.api.v1.migration;

import com.assist.internship.migrationService.entity.Migration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MigrationRepository extends CrudRepository<Migration, String> {
}
