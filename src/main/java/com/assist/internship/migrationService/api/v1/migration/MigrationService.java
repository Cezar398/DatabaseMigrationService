package com.assist.internship.migrationService.api.v1.migration;

import com.assist.internship.migrationService.api.v1.migration.dto.MigrationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MigrationService {
    public void create(MigrationDto bodyData)
    {
        log.info(bodyData.toString());
    }
}
