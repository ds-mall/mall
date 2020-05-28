package com.icoding.controller;

import com.icoding.enums.YesOrNo;
import com.icoding.pojo.Carousel;
import com.icoding.service.CarouselService;
import com.icoding.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(value = "首页", tags = {"首页展示相关接口"})
@RestController
@RequestMapping("/index")
public class IndexController {

  @Autowired
  CarouselService carouselService;

  @ApiOperation(value = "首页轮播图列表接口", notes = "首页轮播图列表", httpMethod = "GET")
  @GetMapping("/carousel")
  public JSONResult queryAllCarousel() {
    List<Carousel> carousels = carouselService.queryAll(YesOrNo.YES.getType());
    return JSONResult.ok(carousels);
  }
}
