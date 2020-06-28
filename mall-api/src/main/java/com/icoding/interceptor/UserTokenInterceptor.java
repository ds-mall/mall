package com.icoding.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义：用户会话请求拦截器
 * @author shengding
 */
@Component
public class UserTokenInterceptor implements HandlerInterceptor {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserTokenInterceptor.class);

  /**
   * 拦截请求，请求访问到controller之前
   * @param request
   * @param response
   * @param handler
   * @return false : 请求被拦截，被驳回
   *         true  : 请求通过拦截器，是OK的，是可以放行的
   * @throws Exception
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    LOGGER.info("请求进入拦截器..., 请求url {}", request.getRequestURL());
    return false;
  }

  /**
   * 请求访问到controller, 渲染视图之前
   * @param request
   * @param response
   * @param handler
   * @param modelAndView
   * @throws Exception
   */
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

  }

  /**
   * 请求访问到controller, 渲染视图之后
   * @param request
   * @param response
   * @param handler
   * @param ex
   * @throws Exception
   */
  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

  }
}
