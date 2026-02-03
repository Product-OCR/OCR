package com.ocr.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    @Schema(description = "Response status code", example = "1000")
    private int code;

    @Schema(description = "Response message", example = "Operation successful")
    private String message;

    @Schema(description = "Response result object data")
    private T result;
}
