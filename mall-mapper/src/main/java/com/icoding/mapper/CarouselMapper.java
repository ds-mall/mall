package com.icoding.mapper;

import com.icoding.my.mapper.MyMapper;
import com.icoding.pojo.Carousel;
import io.swagger.models.auth.In;

import java.util.List;

public interface CarouselMapper extends MyMapper<Carousel> {
  List<Carousel> queryAll(Integer isShow);
}
