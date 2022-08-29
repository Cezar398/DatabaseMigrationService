package com.assist.internship.migrationservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "rating_seq")
    private Long id;
    @Column(name = "content")
    private String rateContent;
    @Column(name = "rate")
    private int rate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
