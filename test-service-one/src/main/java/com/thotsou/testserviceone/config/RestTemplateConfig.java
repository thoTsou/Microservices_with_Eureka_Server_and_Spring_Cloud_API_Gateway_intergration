package com.thotsou.testserviceone.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    // API used to fetch the quotes
    private static final String QUOTES_API_BASE_URL = "https://api.api-ninjas.com/v1";
    private static final String API_KEY_HEADER = "X-Api-Key";
    // visit api-ninjas and register in order to get your own API KEY
    private static final String API_KEY_HEADER_VALUE = "YOUR_API_KEY";

    @Bean
    public RestTemplate buildRestTempate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .rootUri(QUOTES_API_BASE_URL)
                .defaultHeader(API_KEY_HEADER, API_KEY_HEADER_VALUE)
                .build();
    }

}
