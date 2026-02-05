package com.ocr.components.file.model;

import com.ocr.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "files")
@Getter
@Setter
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
}
