package com.thotsou.apigateway.security;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class RouterValidator {

    private static final List<String> openApis = List.of(
            "/auth-service"
    );

    public boolean checkIfApiIsSecured(String apiURI) {
        return openApis.stream().noneMatch(apiURI::contains);
    }

}
