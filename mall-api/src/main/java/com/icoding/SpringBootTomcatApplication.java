package com.icoding;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author shengding
 * war[3] 增加 war的启动类
 */

@SpringBootApplication
public class SpringBootTomcatApplication extends SpringBootServletInitializer {
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    // 指向 MallApplication 这个springboot启动类
    return builder.sources(MallApplication.class);
  }
}
