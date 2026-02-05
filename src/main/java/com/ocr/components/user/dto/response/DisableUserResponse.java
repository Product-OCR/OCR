package com.ocr.components.user.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DisableUserResponse {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String role;
    private String status;
}
