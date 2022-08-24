package com.assist.internship.migrationservice.api.v1.crew;

import com.assist.internship.migrationservice.api.v1.crew.dto.CreateDto;
import com.assist.internship.migrationservice.entity.Crew;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/crew")
@Tag(description = "Crew resources that provides access to available crew", name = "Crew Resource")
public class CrewController {
    private final CrewService crewService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All crew have been selected"),
            @ApiResponse(responseCode = "404", description = "No crews found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @Operation(description = "Get all crew from database", summary = "Get all")
    @GetMapping
    public List<Crew> getAll()
    {
        return crewService.getAll();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crew have been selected"),
            @ApiResponse(responseCode = "404", description = "No crew found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @Operation(description = "Get crew by id from database", summary = "Get crew")
    @GetMapping("/{id}")
    public Crew getById(@Parameter(description = "Crew ID") @PathVariable("id") Long id)
    {
        return crewService.getById(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Crew has been created"),
            @ApiResponse(responseCode = "404", description = "Crew has not been created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @Operation(description = "Create crew", summary = "Create crew")
    @PostMapping
    public void create(@ParameterObject CreateDto createDto)
    {
        crewService.create(createDto);
    }
}
