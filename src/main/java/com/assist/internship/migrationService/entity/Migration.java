package com.assist.internship.migrationService.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "movie", schema = "public")
public class Migration {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String title;
    private String overview;
    private String poster_path;
    private String media_type;
    private String popularity;
    private String release_date;
    private Boolean video;
    private Float vote_average;
    private Integer vote_count;


}
