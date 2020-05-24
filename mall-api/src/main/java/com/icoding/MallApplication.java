package com.icoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
// 扫描 mybatis 通用 mapper 所在的包
@MapperScan(basePackages = "com.icoding.mapper")
public class MallApplication {
  public static void main(String[] args) {
    SpringApplication.run(MallApplication.class, args);
  }
}
