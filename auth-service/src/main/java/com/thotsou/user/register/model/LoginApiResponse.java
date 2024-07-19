package com.thotsou.user.register.model;

import lombok.Data;

@Data
public class LoginApiResponse {
    private final int httpStatusCode;
    private final String responseMessage;
    private final String accessToken;
}
