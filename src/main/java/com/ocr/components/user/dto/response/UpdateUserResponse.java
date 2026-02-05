package com.ocr.components.user.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserResponse {
    private Long id;
    private String accountCode;
    private String username;
    private String fullName;
    private String email;
    private String role;
    private String status;
}
