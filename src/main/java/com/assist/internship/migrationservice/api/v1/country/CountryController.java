package com.assist.internship.migrationservice.api.v1.country;

import com.assist.internship.migrationservice.api.v1.country.dto.CountryDto;
import com.assist.internship.migrationservice.api.v1.country.dto.CreateCountryDto;
import com.assist.internship.migrationservice.entity.Country;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Country", description = "Country operations")
@RequestMapping("/api/v1/country")
public class CountryController {

    private final CountryService countryService;

    @Operation(summary = "Get all countries", description = "Get all countries from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All countries have been selected"),
            @ApiResponse(responseCode = "404", description = "No countries found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @GetMapping()
    public List<Country> getAll()
    {
        return countryService.getAllCountries();
    }

    @Operation(summary = "Create country", description = "Create country in database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Country has been selected"),
            @ApiResponse(responseCode = "404", description = "No country found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @PostMapping()
    public Country createCountry(@Parameter(description = "Country data") @RequestBody CountryDto countryDto)
    {
        return countryService.createCountry(countryDto);
    }

    @Operation(summary = "Add movie to country", description = "Add movie to country and inverse relationship")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie has been added to country"),
            @ApiResponse(responseCode = "404", description = "No movie found"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
    })
    @PostMapping("/movie")
    public Country addMovieToCountry(@RequestBody CreateCountryDto countryDto)
    {
        return countryService.addMovieToCountry(countryDto);
    }

    @DeleteMapping
    public void deleteAll()
    {
        countryService.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") String id)
    {
        countryService.deleteById(id);
    }

}
