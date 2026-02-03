package com.ocr.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Table(name = "cover")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentCover extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cover_gen")
    @SequenceGenerator(name = "cover_gen", sequenceName = "cover_seq", allocationSize = 50)
    private Long id;

    private String name;

    @Column(name = "document_id")
    private String documentId;

    @Column(name = "path")
    private String path;
}
