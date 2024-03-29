package com.assist.internship.migrationservice.api.v1.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.Data;

import java.sql.Date;


@Data
public class MemberDataDto {
    @Parameter(in = ParameterIn.QUERY,description = "Member first name", required = true, example = "Jim")
    private String firstName;
    @Parameter(in = ParameterIn.QUERY,description = "Member last name", required = true, example = "Smith")
    private String lastName;
    @Parameter(in = ParameterIn.QUERY,description = "Member birth date", required = true, example = "2001-07-31")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;
}
