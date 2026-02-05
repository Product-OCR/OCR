package com.ocr.components.project.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "form_project", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"projectId", "formId"})
})
@Builder
@Getter
@Setter
public class FormProject {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "form_project_seq_gen")
    @SequenceGenerator(name = "form_project_seq_gen", sequenceName = "form_project_sequence", allocationSize = 50)
    private Long id;
    private Long projectId;
    private Long formId;
    private String value;
}
