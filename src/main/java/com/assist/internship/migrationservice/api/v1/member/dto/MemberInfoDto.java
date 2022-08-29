package com.assist.internship.migrationservice.api.v1.member.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
}
