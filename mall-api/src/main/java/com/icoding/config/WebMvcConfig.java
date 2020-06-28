package com.icoding.config;

import com.icoding.interceptor.UserTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author shengding
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Autowired
  UserTokenInterceptor userTokenInterceptor;

  /**
   * Springboot自带的http client 用于发送http请求
   * @param builder
   * @return
   */
  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.build();
  }


  /**
   * 注册拦截器(自定义拦截器只有注册了才生效)
   * @param registry
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(userTokenInterceptor)
            .addPathPatterns("/user/hello");
  }
}
