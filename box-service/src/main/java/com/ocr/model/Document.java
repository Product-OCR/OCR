package com.ocr.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cover_gen")
    @SequenceGenerator(name = "cover_gen", sequenceName = "cover_seq", allocationSize = 50)
    private Long id;

    private String documentCode;

    @Column(name = "unit_id")
    private Long unitId;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "editor_staff_id")
    private Long userId;

    @Column(name = "verifier_id")
    private Long verifierId;



}
