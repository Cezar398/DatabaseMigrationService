package com.assist.internship.migrationservice.api.v1.contract.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.Data;

import java.sql.Date;

@Data
public class ContractDataDto {
    @Parameter(in = ParameterIn.QUERY,description = "Contract start date", required = true, example = "2000-07-31")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startDate;
    @Parameter(in = ParameterIn.QUERY,description = "Contract end date", required = true, example = "2002-07-31")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date endDate;
    @Parameter(in = ParameterIn.QUERY,description = "Contract crew id", required = true, example = "1")
    private Long crewId;
    @Parameter(in = ParameterIn.QUERY,description = "Contract movie id", required = true, example = "1")
    private Long movieId;
}
