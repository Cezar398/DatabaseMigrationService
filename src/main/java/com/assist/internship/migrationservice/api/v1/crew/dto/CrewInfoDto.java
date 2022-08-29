package com.assist.internship.migrationservice.api.v1.crew.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrewInfoDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
}
