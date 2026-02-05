package com.ocr.components.task.model;

import com.ocr.common.model.BaseEntity;
import com.ocr.components.task.enums.Assignment;
import com.ocr.components.task.enums.TaskType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "task")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq_gen")
    @SequenceGenerator(name = "task_seq_gen", sequenceName = "task_sequence", allocationSize = 50)
    private Long id;

    private String taskName;

    @Column(name = "unit_id")
    private Long unitId;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "task_type")
    @Enumerated(EnumType.STRING)
    private TaskType taskType;

    @Column(name = "assignment")
    @Enumerated(EnumType.STRING)
    private Assignment assignment;

    @Column(name = "Workload")
    private Long workload;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "assignee_id")
    private Long user_id;

    @Column(name = "progress")
    private Long progress;
}
