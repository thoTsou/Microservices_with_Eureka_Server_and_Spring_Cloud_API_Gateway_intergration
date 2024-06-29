package com.thotsou.apigateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrix
@RequiredArgsConstructor
public class GatewayConfig {

    private final AuthenticationFilter authFilter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("test-service-one", r -> r.path("/test-service-one/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://TEST-SERVICE-ONE"))

                .route("auth-service", r -> r.path("/auth-service/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://AUTH-SERVICE"))

                .build();
    }

}
