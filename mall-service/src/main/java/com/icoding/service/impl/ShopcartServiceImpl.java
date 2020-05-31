package com.icoding.service.impl;

import com.icoding.mapper.ShopcartMapper;
import com.icoding.service.ShopcartService;
import com.icoding.vo.ShopcartItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ShopcartServiceImpl implements ShopcartService {
  @Autowired
  ShopcartMapper shopcartMapper;

  @Override
  public List<ShopcartItemVO> refresh(String itemSpecIds) {
    List<String> specIds = Stream.of(itemSpecIds.split(",")).collect(Collectors.toList());
    List<ShopcartItemVO> result = shopcartMapper.refresh(specIds);
    return result;
  }
}
