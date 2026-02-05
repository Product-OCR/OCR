package com.ocr.common.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Yêu cầu lọc danh sách đơn vị")
public class UnitFilterRequest {

    @Schema(description = "Từ khóa tìm kiếm (tên đơn vị, mã đơn vị...)", example = "DiaChinh")
    private String keyword;

    @Schema(description = "Lọc chính xác theo số lượng dự án", example = "5")
    private Long totalProject;

    @Schema(description = "Lọc từ ngày (Định dạng: ISO DATE-TIME)", example = "2026-02-03T00:00:00")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime fromDate;

    @Schema(description = "Lọc đến ngày (Định dạng: ISO DATE-TIME)", example = "2026-02-03T23:59:59")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime toDate;
}
