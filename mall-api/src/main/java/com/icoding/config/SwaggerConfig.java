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

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  // swagger官方UI 访问url   : http://localhost:port/swagger-ui.html
  // swagger第三方UI 访问url : http://localhost:port/doc.html
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2) //指定api类型为swagger2
            .apiInfo(apiInfo()) // 定义api文档汇总信息
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.icoding.controller")) // 指定controller包
            .paths(PathSelectors.any())
            .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
            .title("个人电商平台接口api") // 文档标题
            .contact(new Contact("icoding", "www.icoding.com", "abc@iconding.com")) // 联系人信息
            .description("接口文档") // 描述
            .version("1.0.1") // 版本号
            .termsOfServiceUrl("https://www.icoding.com") // 网站地址
            .build();
  }
}
