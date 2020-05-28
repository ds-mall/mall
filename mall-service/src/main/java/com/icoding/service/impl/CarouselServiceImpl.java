package com.icoding.service.impl;

import com.icoding.mapper.CarouselMapper;
import com.icoding.pojo.Carousel;
import com.icoding.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {

  @Autowired
  CarouselMapper carouselMapper;

  /**
   * 查看展示轮播图片
   * @param isShow
   * @return
   */
  @Override
  public List<Carousel> queryAll(Integer isShow) {
    return carouselMapper.queryAll(isShow);
  }
}
