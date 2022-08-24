package com.assist.internship.migrationservice.api.v1.crew.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.Data;

import java.util.Date;

@Data
public class CreateDto {
    @Parameter(in = ParameterIn.QUERY,description = "Crew first name")
    private String firstName;
    @Parameter(in = ParameterIn.QUERY,description = "Crew last name")
    private String lastName;
    @Parameter(in = ParameterIn.QUERY,description = "Crew birth date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;
}
