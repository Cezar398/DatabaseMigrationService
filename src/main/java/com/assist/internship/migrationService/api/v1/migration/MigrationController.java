package com.assist.internship.migrationService.api.v1.migration;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/migration")
public class MigrationController {

    MigrationService migrationService;

    @PostMapping(path = "/start")
    public void start(@RequestBody String url)
    {
         migrationService.start(url);
    }
}
