package com.ocr.service.implement;

import com.ocr.dto.request.CountProjectsByUnitIdResponse;
import com.ocr.dto.request.CreateProjectRequest;
import com.ocr.dto.request.ProjectSearchRequest;
import com.ocr.dto.request.UpdateProjectRequest;
import com.ocr.dto.response.ApiResponse;
import com.ocr.dto.response.CreateProjectResponse;
import com.ocr.dto.response.ProjectFilterResponse;
import com.ocr.dto.response.UpdateProjectResponse;
import com.ocr.enums.Approach;
import com.ocr.enums.ErrorCode;
import com.ocr.enums.ProjectStatus;
import com.ocr.exception.BaseException;
import com.ocr.model.FormProject;
import com.ocr.model.Project;
import com.ocr.repository.FormProjectRepository;
import com.ocr.repository.ProjectRepository;
import com.ocr.service.ProjectService;
import com.ocr.utils.GenCodeUtil;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final FormProjectRepository formProjectRepository;
    private final Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Override
    public ApiResponse<CountProjectsByUnitIdResponse> countByUnitId(Long unitId) {
        CountProjectsByUnitIdResponse response = new CountProjectsByUnitIdResponse();
        response.setCount(projectRepository.countByUnitId(unitId));

        return ApiResponse.<CountProjectsByUnitIdResponse> builder()
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .result(response).build();
    }

    @Override
    @Transactional
    public ApiResponse<CreateProjectResponse> createProject(CreateProjectRequest request) {

        GenCodeUtil util = new GenCodeUtil();
        String projectCode = util.generateFastCode("DA");
        Project project = Project.builder()
                .unitId(request.getUnitId())
                .projectCode(projectCode)
                .projectName(request.getProjectName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .approachMethod(Approach.valueOf(request.getApproachMethod()))
                .projectManagerId(request.getProjectManagerId())
                .ocrProgress(0f)
                .verificationProgress(0f)
                .projectStatus(ProjectStatus.OPEN)
                .note(request.getNote())
                .build();

        Project savedProject = projectRepository.save(project);
        projectRepository.incrementUnitProjectCount(savedProject.getUnitId());

        if (request.getFormId() != null && !request.getFormId().isEmpty()) {
            List<FormProject> formProjects = request.getFormId().stream()
                    .map(formId -> FormProject.builder()
                            .projectId(savedProject.getId())
                            .formId(formId)
                            .build())
                    .collect(Collectors.toList());

            formProjectRepository.saveAll(formProjects);
        }

        CreateProjectResponse response = CreateProjectResponse.builder()
                .projectId(savedProject.getId())
                .unitId(savedProject.getUnitId())
                .projectName(savedProject.getProjectName())
                .projectCode(project.getProjectCode())
                .formId(request.getFormId())
                .build();

        return ApiResponse.<CreateProjectResponse>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .result(response)
                .build();
    }

    @Override
    public ApiResponse<UpdateProjectResponse> updateProject(Long projectId, UpdateProjectRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new BaseException(ErrorCode.PROJECT_NOT_EXISTED));

        project.setProjectName(request.getProjectName());
        project.setUnitId(request.getUnitId());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        project.setNote(request.getNote());
        project.setProjectManagerId(request.getProjectManagerId());

        if (request.getApproachMethod() != null) {
            project.setApproachMethod(Approach.valueOf(request.getApproachMethod()));
        }
        if (request.getStatus() != null) {
            project.setProjectStatus(ProjectStatus.valueOf(request.getStatus()));
        }

        projectRepository.save(project);

        formProjectRepository.deleteByProjectId(projectId);

        if (request.getFormIds() != null && !request.getFormIds().isEmpty()) {
            List<FormProject> newForms = request.getFormIds().stream()
                    .map(formId -> FormProject.builder()
                            .projectId(projectId)
                            .formId(formId)
                            .build())
                    .collect(Collectors.toList());
            formProjectRepository.saveAll(newForms);
        }
        UpdateProjectResponse response = UpdateProjectResponse.builder()
                .projectId(projectId)
                .unitId(request.getUnitId())
                .projectName(request.getProjectName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .approachMethod(request.getApproachMethod())
                .projectManagerId(request.getProjectManagerId())
                .formId(request.getFormIds())
                .note(request.getNote())
                .build();

        return ApiResponse.<UpdateProjectResponse>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .result(response).build();
    }

    @Override
    public ApiResponse<Page<ProjectFilterResponse>> searchProjects(ProjectSearchRequest request) {
        // 1. Log monitoring theo ý anh Sơn
        logger.info("Bắt đầu lọc dự án: Keyword={}, UnitId={}, Page={}",
                request.getKeyword(), request.getUnitId(), request.getPage());

        // 2. Cấu hình phân trang (Size=20, Sort=Recently Created)
        int pageSize = (request.getSize() <= 0) ? 20 : request.getSize();
        Pageable pageable = PageRequest.of(request.getPage(), pageSize,
                Sort.by("createdAt").descending());

        // 3. Build bộ lọc động Specification
        Specification<Project> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(request.getKeyword())) {
                String pattern = "%" + request.getKeyword().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("projectName")), pattern),
                        cb.like(cb.lower(root.get("projectCode")), pattern)
                ));
            }

            if (request.getUnitId() != null) {
                predicates.add(cb.equal(root.get("unitId"), request.getUnitId()));
            }

            if (StringUtils.hasText(request.getStatus())) {
                predicates.add(cb.equal(root.get("projectStatus"), ProjectStatus.valueOf(request.getStatus())));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // 4. Lấy danh sách Project
        Page<Project> entities = projectRepository.findAll(spec, pageable);

        // 5. Gom ID để tra cứu dữ liệu liên quan (Unit và Template)
        Set<Long> projectIds = entities.getContent().stream().map(Project::getId).collect(Collectors.toSet());
        Set<Long> unitIds = entities.getContent().stream().map(Project::getUnitId).collect(Collectors.toSet());
        Set<Long> managerIds = entities.getContent().stream()
                .map(Project::getProjectManagerId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // 5.1 Tra cứu Tên Đơn vị
        Map<Long, String> unitMap = new HashMap<>();
        if (!unitIds.isEmpty()) {
            List<Object[]> unitResults = projectRepository.findUnitNamesByUnitIds(unitIds);
            unitMap = unitResults.stream().collect(Collectors.toMap(
                    obj -> ((Number) obj[0]).longValue(),
                    obj -> (String) obj[1]
            ));
        }

        Map<Long, String> managerMap = new HashMap<>();
        if (!managerIds.isEmpty()) {
            List<Object[]> managerResults = projectRepository.findManagerNamesByIds(managerIds);
            managerMap = managerResults.stream().collect(Collectors.toMap(
                    obj -> ((Number) obj[0]).longValue(),
                    obj -> (String) obj[1]
            ));
        }

        // 5.2 Tra cứu Tên Biểu mẫu (Phase 1 lấy 1 tên)
        // Logic tra cứu Template trong hàm searchProjects
        Map<Long, String> templateMap = new HashMap<>();
        if (!projectIds.isEmpty()) {
            List<Object[]> templateResults = projectRepository.findTemplateNamesByProjectIds(projectIds);

            for (Object[] row : templateResults) {
                if (row[0] != null && row[1] != null) {
                    // Sử dụng Number để xử lý linh hoạt cả Long/BigInteger từ Native Query
                    Long pId = ((Number) row[0]).longValue();
                    String tName = row[1].toString();

                    // Phase 1: Lấy tên template đầu tiên cho mỗi project
                    templateMap.putIfAbsent(pId, tName);
                }
            }
        }

        // 6. Mapping sang Response DTO
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Map<Long, String> finalUnitMap = unitMap;
        Map<Long, String> finalManagerMap = managerMap;

        Page<ProjectFilterResponse> response = entities.map(project -> ProjectFilterResponse.builder()
                .projectId(project.getId())
                .managerName(finalManagerMap.getOrDefault(project.getProjectManagerId(), "Chưa gán")) // Thêm Manager.projectCode(project.getProjectCode())
                .projectName(project.getProjectName())
                .unitName(finalUnitMap.getOrDefault(project.getUnitId(), "N/A"))
                .templateName(templateMap.getOrDefault(project.getId(), "N/A")) // Tên biểu mẫu
                .projectStatus(project.getProjectStatus() != null ? project.getProjectStatus().name() : "")
                .approachMethod(project.getApproachMethod() != null ? project.getApproachMethod().name() : "")
                .actualFinishDate(project.getActualFinishDate() != null ? project.getActualFinishDate() : "")
                .timeRange(project.getStartDate() != null && project.getEndDate() != null
                        ? project.getStartDate().format(formatter) + " - " + project.getEndDate().format(formatter)
                        : "") // Hiển thị chuẩn UI
                .ocrProgress(project.getOcrProgress())
                .verificationProgress(project.getVerificationProgress())
                .build()
        );

        logger.info("Tìm kiếm xong. Tổng bản ghi: {}", entities.getTotalElements());

        return ApiResponse.<Page<ProjectFilterResponse>>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .message(ErrorCode.SUCCESS.getMessage())
                .result(response)
                .build();
    }
}
