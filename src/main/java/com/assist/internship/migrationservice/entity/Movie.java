package com.assist.internship.migrationservice.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
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
    @OneToMany(mappedBy = "movie")
    private List<Rating> ratings = new ArrayList<>();
}
