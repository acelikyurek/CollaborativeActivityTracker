package com.example.spring_projects.collaborativeactivitytracker.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "API", version = "v1"))
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "bearerAuth", bearerFormat = "JWT", scheme = "bearer")
public class SwaggerConfiguration {}
