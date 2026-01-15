package com.flowershop.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {
    
    @Bean
    @Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        return builder.build()
                .addMixIn(Object.class, IgnoreHibernateProperties.class);
    }
    
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private abstract class IgnoreHibernateProperties {}
}
