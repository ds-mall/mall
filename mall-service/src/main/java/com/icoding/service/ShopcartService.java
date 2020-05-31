package com.icoding.service;

import com.icoding.vo.ShopcartItemVO;

import java.util.List;

public interface ShopcartService {
  /**
   * 刷新购物车，根据规格id所拼接的字符串查询商品规格信息
   * @param itemSpecIds  "1001, 1002, 1003"
   * @return
   */
  List<ShopcartItemVO> refresh(String itemSpecIds);
}
