package com.web.project.config;

import org.springframework.context.annotation.Configuration;
<<<<<<< HEAD
import org.springframework.lang.NonNull;
=======
>>>>>>> 661b8442d2d6ea78fb5c0006aa4ecc5e30213384
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
   @Override
<<<<<<< HEAD
   public void addCorsMappings(@NonNull CorsRegistry registry) {
=======
   public void addCorsMappings(CorsRegistry registry) {
>>>>>>> 661b8442d2d6ea78fb5c0006aa4ecc5e30213384
       registry.addMapping("/rest/**");
   }
}
