package com.assist.internship.migrationservice.api.v1.migration;

import com.assist.internship.migrationservice.api.v1.migration.dto.MigrationDto;
import com.assist.internship.migrationservice.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/movie")
@RequiredArgsConstructor
public class MigrationController {

    private final MigrationService migrationService;

    @PostMapping()
    public void create(MigrationDto migration) {
        migrationService.create(migration);
    }

    @GetMapping()
    public List<Movie> getAll() {
        return migrationService.getAll();
    }

    @GetMapping(path = "/{id}")
    public Optional<Movie> getById(@PathVariable("id") String id) {
        return migrationService.getById(id);
    }

    @DeleteMapping()
    public void deleteAll() {
        migrationService.deleteAll();
    }

    @DeleteMapping(path = "/{id}")
    public void deleteAll(@PathVariable("id") String id) {
        migrationService.deleteById(id);
    }

    @PutMapping(path = "/{id}")
    public void updateById(@PathVariable("id") String id, MigrationDto migrationDto) {
        migrationService.updateById(id, migrationDto);
    }
}
