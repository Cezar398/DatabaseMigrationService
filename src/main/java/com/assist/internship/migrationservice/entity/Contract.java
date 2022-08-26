package com.assist.internship.migrationservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;

@Data
@Table(name = "contract")
@Entity
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "contract_seq")
    private Long id;
    @Column(name = "start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @Column(name = "end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    @ManyToOne
    @JoinColumn(name = "crew_id")
    @JsonBackReference
    private Crew crew;
    @OneToOne
    @JoinColumn(name="movie_id", referencedColumnName = "id")
    @JsonManagedReference
    private Movie movie;
}
