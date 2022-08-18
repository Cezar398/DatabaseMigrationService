package com.assist.internship.migrationservice.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "contract")
@Entity
public class Contracts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "contract_seq")
    private Long id;
    @Column(name = "start_date")
    private String startDate;
    @Column(name = "end_date")
    private String endDate;
    @ManyToOne
    @JoinColumn(name = "crew_id")
    private Crew crew;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="movie_id", referencedColumnName = "id")
    private Movie movie;
}
