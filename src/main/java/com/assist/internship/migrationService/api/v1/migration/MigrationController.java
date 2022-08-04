package com.assist.internship.migrationService.api.v1.migration;

import com.assist.internship.migrationService.api.v1.migration.dto.MigrationDto;
import com.assist.internship.migrationService.entity.Migration;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping(path = "/getAll")
    public List<Migration> getAll()
    {
        return migrationService.getAll();
    }
    @GetMapping(path = "/get/{id}")
    public Optional<Migration> getById(@PathVariable("id") String id)
    {
        return migrationService.getById(id);
    }

    @DeleteMapping(path = "/deleteAll")
    public void deleteAll()
    {
        migrationService.deleteAll();
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteAll(@PathVariable("id") String id)
    {
        migrationService.deleteById(id);
    }

    @PutMapping(path = "/update/{id}")
    public void updateById(@PathVariable("id") String id, MigrationDto migrationDto)
    {
        migrationService.updateById(id, migrationDto);
    }
}
