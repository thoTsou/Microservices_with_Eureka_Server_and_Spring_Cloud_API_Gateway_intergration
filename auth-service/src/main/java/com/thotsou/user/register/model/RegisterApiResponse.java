package com.thotsou.user.register.model;

import lombok.Data;

@Data
public class RegisterApiResponse {
    private final int httpStatusCode;
    private final String responseMessage;
}
