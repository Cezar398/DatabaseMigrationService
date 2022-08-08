package com.assist.internship.migrationservice.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column(name = "content")
    private String rateContent;
    @Column(name = "rate")
    private int rate;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
