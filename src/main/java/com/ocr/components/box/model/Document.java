package com.ocr.components.box.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "document")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doc_gen")
    @SequenceGenerator(name = "doc_gen", sequenceName = "document_seq", allocationSize = 50)
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
