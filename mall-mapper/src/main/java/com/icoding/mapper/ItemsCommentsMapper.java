package com.icoding.mapper;

import com.icoding.my.mapper.MyMapper;
import com.icoding.pojo.ItemsComments;
import com.icoding.vo.ItemCommentLevelAndCountVO;
import com.icoding.vo.ItemCommentVO;
import io.swagger.models.auth.In;

import java.util.List;

public interface ItemsCommentsMapper extends MyMapper<ItemsComments> {
   Integer queryItemCommentCountByItemIdAndLevel(String itemId, Integer level);
   List<ItemCommentVO> queryItemComments(String itemId, Integer level, Integer start, Integer end);
}
