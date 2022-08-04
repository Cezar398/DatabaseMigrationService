package com.assist.internship.migrationService.api.v1.migration;

import com.assist.internship.migrationService.api.v1.migration.dto.MigrationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/migration")
@RequiredArgsConstructor
public class MigrationController {

    private final MigrationService migrationService;

    @PostMapping(path = "/create")
    public void create(MigrationDto migration)
    {
         migrationService.create(migration);
    }
}
