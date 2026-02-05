package com.ocr.components.project.model;

import com.ocr.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "form")
public class FormTemplate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "form_seq_gen")
    @SequenceGenerator(name = "form_seq_gen", sequenceName = "form_sequence", allocationSize = 50)
    private Long id;

    @Column(name = "field")
    private String field;
}
