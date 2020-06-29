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
            .addPathPatterns("/shopcart/add")
            .addPathPatterns("/shopcart/del")

            .addPathPatterns("/address/list")
            .addPathPatterns("/address/add")
            .addPathPatterns("/address/update")
            .addPathPatterns("/address/setDefault")
            .addPathPatterns("/address/del")

            .addPathPatterns("/orders/getPaidOrderInfo")
            .addPathPatterns("/orders/items")
            .addPathPatterns("/orders/receive")
            .addPathPatterns("/orders/create")

            .addPathPatterns("/comments/list")
            .addPathPatterns("/comments")

            .addPathPatterns("/center/trend")
            .addPathPatterns("/center/statusCounts")
            .addPathPatterns("/center/orders")
            .addPathPatterns("/center/userface")
            .addPathPatterns("/center/userInfo")

            .addPathPatterns("/pay/getPaidOrderInfo");
  }
}
