package com.assist.internship.migrationservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="crew")
public class Crew {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "crew_seq")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birth_date")
    private String birthDate;
    @OneToMany(mappedBy = "crew")
    private List<Contracts> contractsList;
}
