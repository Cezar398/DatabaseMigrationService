package com.assist.internship.migrationservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonBackReference
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    @JsonBackReference
    private Movie movie;
}
