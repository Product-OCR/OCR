package com.ocr.model;

import com.ocr.enums.Form;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.ocr.model.Users;

import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
@AllArgsConstructor
@NoArgsConstructor
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq_gen")
    @SequenceGenerator(
            name = "project_seq_gen",
            sequenceName = "project_sequence",
            allocationSize = 50 // Cực kỳ quan trọng để tối ưu batch
    )
    @Column(name = "id")
    private Long id;

    @Column(name = "unit_id")
    private Long unitId;

    @Column(name = "project_name", nullable = false)
    private String projectName;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "approach_method", nullable = false)
    private String approachMethod;

    @JoinColumn(name = "project_manager_id")
    private Users projectManager;

    @Column(name = "ocr_progres")
    private Float ocrProgress;

    @Column(name = "verification_progress")
    private Float verificationProgress;

    @Column(name = "form")
    private Form form;
}
