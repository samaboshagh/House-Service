package org.example.finalprojectphasetwo;

import lombok.NonNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class FinalProjectPhaseTwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalProjectPhaseTwoApplication.class, args);

    }
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/captcha/**").addResourceLocations("file:/path/to/captcha/images/");
            }
        };
    }
}