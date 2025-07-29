package com.cse.journalApp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  //this annotaion declares that there is a bean exists in the perticular class.
public class webconfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/journal/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("*");
                registry.addMapping("/public")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("*");
            }
        };
    }
}
