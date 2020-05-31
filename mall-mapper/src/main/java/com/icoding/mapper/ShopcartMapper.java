package com.icoding.mapper;

import com.icoding.my.mapper.MyMapper;
import com.icoding.vo.ShopcartItemVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopcartMapper extends MyMapper<ShopcartItemVO> {
  List<ShopcartItemVO> refresh(@Param("list") List<String> itemSpecIds);
}
