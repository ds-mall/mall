package com.icoding.mapper;

import com.icoding.my.mapper.MyMapper;
import com.icoding.pojo.ItemsComments;
import com.icoding.vo.ItemCommentVO;
import com.icoding.vo.UserCenterCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemsCommentsMapper extends MyMapper<ItemsComments> {
   int getUserCommentsCounts(@Param("userId") String userId);
   List<UserCenterCommentVO> getUserComments(
           @Param("userId") String userId,
           @Param("start") Integer start,
           @Param("end") Integer end
           );
   Integer queryItemCommentCountByItemIdAndLevel(String itemId, Integer level);
   List<ItemCommentVO> queryItemComments(String itemId, Integer level, Integer start, Integer end);
   int batchInsertComments(@Param("commentsList") List<ItemsComments> commentsList);
}
