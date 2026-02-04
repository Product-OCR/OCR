package com.ocr.model;

import com.ocr.enums.Approach;
import com.ocr.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq_gen")
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

    @Column(name = "priject_code")
    private String projectCode;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "approach_method", nullable = false)
    private Approach approachMethod;

    @Column(name = "project_manager_id")
    private Long projectManagerId;

    @Column(name = "ocr_progres")
    private Float ocrProgress;

    @Column(name = "verification_progress")
    private Float verificationProgress;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProjectStatus projectStatus;

    @Column(name = "actual_finish_date")
    private String actualFinishDate;

    @Column(name = "note")
    private String note;
}
