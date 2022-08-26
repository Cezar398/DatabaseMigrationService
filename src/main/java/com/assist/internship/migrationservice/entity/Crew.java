package com.assist.internship.migrationservice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    @OneToMany(mappedBy = "crew", orphanRemoval = true)
    @JsonManagedReference
    private List<Contract> contractList;
}
