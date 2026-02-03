package com.ocr.model;

import com.ocr.enums.OcrStatus;
import com.ocr.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "box")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Box extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "box_seq_gen")
    @SequenceGenerator(
            name = "box_seq_gen",
            sequenceName = "box_sequence",
            allocationSize = 50 // Cực kỳ quan trọng để tối ưu batch
    )
    private Long id;
    @Column(name = "box_code", unique = true)
    private String boxCode;

    @Column(unique = true, nullable = false)
    private String boxNumber;

    @Column(name = "status")
    private OcrStatus status;

    @Column(name = "task_status")
    private TaskStatus taskStatus;

    @Column(name = "editor_staff_id")
    private Long editorStaffId;

    @Column(name = "verifier_id")
    private Long verifierId;

    private Long totalDocuments;

    private Long totalFiles;

    @Column(name = "unit_id")
    private Long unitId;

    @Column(name = "project_id")
    private Long projectId;






}
