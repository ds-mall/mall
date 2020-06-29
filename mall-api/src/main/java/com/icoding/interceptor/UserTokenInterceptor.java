package com.icoding.interceptor;

import com.icoding.enums.RedisKey;
import com.icoding.utils.JSONResult;
import com.icoding.utils.JsonUtils;
import com.icoding.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 自定义：用户会话请求拦截器
 * 请求->过滤器->拦截器的preHandle()->控制器Controller->拦截器的postHandle()->视图页面渲染->拦截器的afterCompletion()
 * @author shengding
 */
@Component
public class UserTokenInterceptor implements HandlerInterceptor {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserTokenInterceptor.class);

  @Autowired
  RedisOperator redisOperator;

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

    // 对于复杂的Ajax 跨域请求，浏览器会首先发送一个 OPTIONS 请求，如果返回200，则继续发送真正的请求
    // 配置了拦截器后，由于 OPTIONS请求时不携带 自定义header的，所以 preHandle返回false, OPTIONS请求被拦截，
    // 后续跨域设置失效
    if(HttpMethod.OPTIONS.name().equals(request.getMethod().toUpperCase())) {
      return true;
    }

    // http请求header中携带的token
    String requestHeaderUserToken = request.getHeader("headerUserToken");

    // http请求header中携带的userId
    String requestHeaderUserId = request.getHeader("headerUserId");
    String redisUserToken = redisOperator.get(RedisKey.USERTOKEN.getKey() + ":" + requestHeaderUserId);

    LOGGER.info("请求进入拦截器--->, 请求header中userId: {}, token: {}; redis中用户token: {}", requestHeaderUserId, requestHeaderUserToken, redisUserToken);
    // http携带token不为空且与redis所存的token一致，则通过拦截，否则视为非法请求
    if(StringUtils.isNotBlank(requestHeaderUserId) && StringUtils.isNotBlank(requestHeaderUserToken)) {
      if(StringUtils.isBlank(redisUserToken)) {
        returnErrorResponse(response, JSONResult.errMsg("请登录..."));
        return false;
      } else {
        if(!requestHeaderUserToken.equals(redisUserToken)) {
          returnErrorResponse(response, JSONResult.errMsg("账号异地登录..."));
          return false;
        }
      }
    } else {
      returnErrorResponse(response, JSONResult.errMsg("请登录..."));
      return false;
    }
    return true;
  }

  /**
   * 由于拦截器 preHandle 返回 boolean值， 当请求被拦截时若想返回json格式，需要手动设置response
   * @param response
   * @param jsonResult
   */
  public void returnErrorResponse(HttpServletResponse response, JSONResult jsonResult) {
    response.setContentType("application/json");
    response.setCharacterEncoding("utf-8");
    try(OutputStream outputStream = response.getOutputStream()) {
      outputStream.write(JsonUtils.objectToJson(jsonResult).getBytes());
      outputStream.flush();
    } catch(IOException e) {
      e.printStackTrace();
    }
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
