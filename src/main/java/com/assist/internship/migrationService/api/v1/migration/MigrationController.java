package com.assist.internship.migrationService.api.v1.migration;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MigrationController {

    @PostMapping(path = "/start")
    public void start()
    {
        System.out.println("start");
    }
}
