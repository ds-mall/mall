package com.icoding.config;

import com.icoding.service.OrdersService;
import com.icoding.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 订单定时任务
 * cron表达式在线生成: https://qqe2.com/cron
 *
 * 使用定时任务关闭超时订单有一下弊端
 *  1. 会有时间差，程序不严谨
 *    10：39 下单， 11：00 检查不足1小时， 12：00检查，实际超时时间为 1小时21分钟
 *  2. 不支持集群
 *    单机没毛病，使用集群后，每台机器上都会执行定时任务
 *    解决方案: 单独部署一台机器，用来单独部署定时任务
 *  3. 会对数据库进行全表搜索，及其影响数据库性能(定时任务，仅仅适用于小型项目)
 *    解决方案: 使用消息队列如： RabbitMQ, kafka
 *
 */
@Component
public class OrderTimerTask {

  @Autowired
  OrdersService ordersService;

  /**
   * 0点开始，每隔1小时执行一次
   */
  @Scheduled(cron = "0 0 0/1 * * ?")
  public void autoClose() {
    ordersService.closeOrdersWhoseStatusIsWaitPayAndTimeOut();
  }
}
