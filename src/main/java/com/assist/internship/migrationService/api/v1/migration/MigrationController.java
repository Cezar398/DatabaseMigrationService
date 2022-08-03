package com.assist.internship.migrationService.api.v1.migration;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MigrationController {

    MigrationService migrationService;

    @PostMapping(path = "/start")
    public void start(@RequestBody String url)
    {
         migrationService.start(url);
    }
}
