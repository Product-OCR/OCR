package com.ocr.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "form_project", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"projectId", "formId"})
})
@Builder
public class FormProject {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "form_project_seq_gen")
    @SequenceGenerator(
            name = "form_project_seq_gen",
            sequenceName = "form_project_sequence",
            allocationSize = 50 // Cực kỳ quan trọng để tối ưu batch
    )
    private Long id;
    private Long projectId;
    private Long formId;
    private String value;
}
