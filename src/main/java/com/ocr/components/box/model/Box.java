package com.ocr.components.box.model;

import com.ocr.common.model.BaseEntity;
import com.ocr.components.box.enums.OcrStatus;
import com.ocr.components.box.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "box")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Box extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "box_seq_gen")
    @SequenceGenerator(name = "box_seq_gen", sequenceName = "box_sequence", allocationSize = 50)
    private Long id;

    @Column(name = "box_code", unique = true)
    private String boxCode;

    @Column(unique = true, nullable = false)
    private String boxNumber;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OcrStatus status;

    @Column(name = "task_status")
    @Enumerated(EnumType.STRING)
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
