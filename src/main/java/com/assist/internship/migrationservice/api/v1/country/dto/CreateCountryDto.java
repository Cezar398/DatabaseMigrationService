package com.assist.internship.migrationservice.api.v1.country.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.Data;

@Data
public class CreateCountryDto {
    private String CountryId;
    private String MovieId;
}
