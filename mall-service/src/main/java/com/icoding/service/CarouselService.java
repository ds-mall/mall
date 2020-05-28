package com.icoding.service;

import com.icoding.pojo.Carousel;

import java.util.List;

/**
 * 轮播图
 */
public interface CarouselService {
  List<Carousel> queryAll(Integer isShow);
}
