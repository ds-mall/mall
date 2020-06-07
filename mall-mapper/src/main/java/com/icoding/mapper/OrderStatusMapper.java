package com.icoding.mapper;

import com.icoding.bo.PayjsNotifyBO;
import com.icoding.my.mapper.MyMapper;
import com.icoding.pojo.OrderStatus;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

public interface OrderStatusMapper extends MyMapper<OrderStatus> {
  void updateOrderStatus(@Param("payjsNotifyBO") PayjsNotifyBO payjsNotifyBO, @Param("orderStatus") Integer orderStatus);
}
