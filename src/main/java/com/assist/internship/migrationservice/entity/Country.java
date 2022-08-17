package com.assist.internship.migrationservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "country_seq")
    private Long id;
    @Column(name = "name")
    private String name;
    @JsonIgnore
    @ManyToMany(mappedBy = "countries")
    private List<Movie> movieList = new ArrayList<>();
}

