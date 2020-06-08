package com.icoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
// 扫描 mybatis 通用 mapper 所在的包
@MapperScan(basePackages = "com.icoding.mapper")
// 因新增全局id生成工具类，重新指定组件扫描的基础包
@ComponentScan(basePackages = {"com.icoding", "org.n3r"})
// 开启定时任务
@EnableScheduling
public class MallApplication {
  public static void main(String[] args) {
    SpringApplication.run(MallApplication.class, args);
  }
}
