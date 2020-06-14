package com.icoding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger官方UI 访问url   : http://localhost:port/swagger-ui.html
 * swagger第三方UI 访问url : http://localhost:port/doc.html
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
  @Bean
  public Docket api() {
    //指定api类型为swagger2
    return new Docket(DocumentationType.SWAGGER_2)
            // 定义api文档汇总信息
            .apiInfo(apiInfo())
            .select()
            // 指定controller包
            .apis(RequestHandlerSelectors.basePackage("com.icoding.controller"))
            .paths(PathSelectors.any())
            .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
            // 文档标题
            .title("个人电商平台接口api")
            // 联系人信息
            .contact(new Contact("icoding", "www.icoding.com", "317010370@qq.com"))
            // 描述
            .description("接口文档")
            // 版本号
            .version("1.0.1")
            // 网站地址
            .termsOfServiceUrl("https://www.icoding.com")
            .build();
  }
}
