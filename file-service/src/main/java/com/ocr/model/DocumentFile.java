package com.ocr.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "files")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_gen")
    @SequenceGenerator(name = "file_gen", sequenceName = "file_seq", allocationSize = 50)
    private Long id;

    private String name;

    @Column(name = "document_id")
    private String documentId;

    @Column(name = "path")
    private String path;

    @Column(name = "raw_data")
    private String rawData;

    // các trường thôn tin

}
