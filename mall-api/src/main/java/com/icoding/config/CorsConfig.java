package com.icoding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Springboot 跨域解决方案
 * 官方参考: https://spring.io/guides/gs/rest-service-cors/
 */
@Configuration
public class CorsConfig {
  @Bean("webMvcConfigurer")
  @Profile("dev")
  public WebMvcConfigurer corsConfigurerDev() {
    WebMvcConfigurer webMvcConfigurer = new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")
                // 允许跨域的请求方式
                .allowedMethods("*")
                // 设置允许的header
                .allowedHeaders("*")
                // 是否发送cookie
                .allowCredentials(true);
      }
    };
    return webMvcConfigurer;
  }

  @Bean("webMvcConfigurer")
  @Profile("prod")
  public WebMvcConfigurer corsConfigurerProd() {
    WebMvcConfigurer webMvcConfigurer = new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://47.105.41.229:8080")
                // 允许跨域的请求方式
                .allowedMethods("*")
                // 设置允许的header
                .allowedHeaders("*")
                // 是否发送cookie
                .allowCredentials(true);
      }
    };
    return webMvcConfigurer;
  }
}

