package com.ocr.components.file.model;

import com.ocr.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cover")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DocumentCover extends BaseEntity {

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
