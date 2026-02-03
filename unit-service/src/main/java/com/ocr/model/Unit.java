package com.ocr.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "units")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Unit extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq_gen")
    @SequenceGenerator(
            name = "unit_seq_gen",
            sequenceName = "unit_sequence",
            allocationSize = 50
    )
    private Long id;

    @Column(name = "unit_name", nullable = false)
    private String unitName;

    @Column(name = "unit_code")
    private String unitCode;//

    @Column(name = "total_projects")
    private Integer totalProjects;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "address")
    private String address;
}
