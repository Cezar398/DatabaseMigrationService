package com.assist.internship.migrationService.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Data
@Table(name = "migrations")
@Entity
public class Migration {
    @Id
    private String id;
    private String title;
    private String overview;
    private String poster_path;
    private String media_type;
    private String popularity;
    private Date release_date;
    private Boolean video;
    private Float vote_average;
    private Integer vote_count;
}
