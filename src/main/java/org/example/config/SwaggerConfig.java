package org.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("测试项目 API 文档")
                        .description("使用 Spring Boot 3 + MyBatis Plus")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Your Name")
                                .email("your.email@example.com")));
    }
} 