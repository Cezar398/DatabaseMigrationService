package com.assist.internship.migrationservice.api.v1.contract.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractInfoDto {
    private Long id;
    private Date startDate;
    private Date endDate;
}
