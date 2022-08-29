package com.assist.internship.migrationservice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "movie_seq")
    private Long id;
    @Column(name = "external_id")
    private String externalId;
    private String title;
    private String overview;
    @Column(name = "poster_path")
    private String posterPath;
    @Column(name = "media_type")
    private String mediaType;
    private String popularity;
    @Column(name = "release_date")
    private String releaseDate;
    private Boolean video;
    @Column(name = "vote_average")
    private Float voteAverage;
    @Column(name = "vote_count")
    private Integer voteCount;
    @OneToMany(mappedBy = "movie", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Rating> ratings;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "movie_country", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "country_id"))
    @JsonManagedReference
    private List<Country> countries = new ArrayList<>();
    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Contract> contracts = new ArrayList<>();
}
