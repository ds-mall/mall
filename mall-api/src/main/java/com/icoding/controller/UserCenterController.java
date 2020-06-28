package com.icoding.controller;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.icoding.bo.UpdatedUserBO;
import com.icoding.config.AliyunOssConfig;
import com.icoding.enums.RedisKey;
import com.icoding.pojo.OrderStatus;
import com.icoding.pojo.Users;
import com.icoding.service.OrdersService;
import com.icoding.service.UsersService;
import com.icoding.utils.*;
import com.icoding.vo.OrderStatusCountVO;
import com.icoding.vo.UserCenterOrderVO;
import com.icoding.vo.UsersVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Api(value = "用户中心", tags = {"用户中心相关接口"})
@RestController
@RequestMapping("/center")
public class UserCenterController {

  @Autowired
  UsersService usersService;

  @Autowired
  OrdersService ordersService;

  @Autowired
  AliyunOssConfig aliyunOssConfig;

  @Autowired
  RedisOperator redisOperator;

  @ApiOperation(value = "获取用户信息", notes = "获取用户信息", httpMethod = "GET")
  @GetMapping("/statusCounts")
  public JSONResult getOrderStatusCounts(
          @ApiParam(name = "userId", value = "用户id", required = true)
          @RequestParam("userId") String userId
  ) {
    if(StringUtils.isBlank(userId)) {
      return JSONResult.errMsg("userId为空");
    }
    OrderStatusCountVO orderStatusCountVO = ordersService.getOrderStatusCounts(userId);
    return JSONResult.ok(orderStatusCountVO);
  }

  @ApiOperation(value = "获取用户信息", notes = "获取用户信息", httpMethod = "GET")
  @GetMapping("/userInfo")
  public JSONResult getUserInfo(
          @ApiParam(name = "userId", value = "用户id", required = true)
          @RequestParam("userId") String userId
  ) {
    if(StringUtils.isBlank(userId)) {
      return JSONResult.errMsg("userId为空");
    }
    Users user = usersService.queryUserInfo(userId);
    if(user == null) {
      return JSONResult.errMsg("用户不存在");
    }
    return JSONResult.ok(user);
  }

  @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "PUT")
  @PutMapping("/userInfo")
  public JSONResult updateUserInfo(
          HttpServletRequest req,
          HttpServletResponse rep,
          @ApiParam(name = "userId", value = "用户id", required = true)
          @RequestParam("userId") String userId,
          @ApiParam(name = "更新后用户信息", value = "updated userInfo", required = true)
          @RequestBody UpdatedUserBO updatedUserBO
          ) {
    if(StringUtils.isBlank(userId)) {
      return JSONResult.errMsg("用户id不能为空");
    }
    JSONResult result = checkUpdatedUserInfo(updatedUserBO);
    if(result.getStatus() == 200) {
      usersService.updateUserInfo(userId, updatedUserBO);

      // 更新cookie中的user信息
      updateUserCookie(req, rep, userId);

      return JSONResult.ok("更新成功");
    } else {
      return result;
    }
  }

  @ApiOperation(value = "上传用户头像", notes = "上传用户头像", httpMethod = "POST")
  @PostMapping("/userface")
  public JSONResult updateUserFace(
          HttpServletRequest req,
          HttpServletResponse rep,
          @ApiParam(name = "userId", value = "用户id", required = true)
          @RequestParam("userId") String userId,
          @ApiParam(name = "头像文件", value = "头像文件", required = true)
          @RequestParam("file") MultipartFile file
  ) {
    String fileName = file.getOriginalFilename();
    String suffix = fileName.substring(fileName.lastIndexOf("."));
    String uploadFileName = userId + suffix;

    //1 创建OSSClient实例
    OSS ossClient = new OSSClientBuilder().build(
            aliyunOssConfig.getEndpoint(),
            aliyunOssConfig.getAccessKeyId(),
            aliyunOssConfig.getAccessKeySecret());
    // 2 上传图片
    try {
      PutObjectRequest putObjectRequest = new PutObjectRequest(
              aliyunOssConfig.getBucketName(),
              uploadFileName,
              new ByteArrayInputStream(file.getBytes()));
      ossClient.putObject(putObjectRequest);
    } catch (OSSException oe) {
      return JSONResult.errMsg(oe.getErrorMessage());
    } catch (ClientException ce) {
      return JSONResult.errMsg(ce.getErrorMessage());
    } catch(IOException e) {
      return JSONResult.errMsg(e.getMessage());
    } finally {
      if (ossClient != null) {
        ossClient.shutdown();
      }
    }
    // TODO 上传图片到阿里云oss后，访问url打开默认是下载图片， 需添加自定义域名才能访问

    // 3 拼接上传后图片url  BucketName.Endpoint/ObjectName
    String faceUrl = aliyunOssConfig.getBucketName() + "." + aliyunOssConfig.getEndpoint() + "/" + uploadFileName;
    // 4 更新用户数据库的头像
    usersService.updateUserFace(userId, faceUrl);

    // 5 更新cookie中的user信息
    updateUserCookie(req, rep, userId);

    return JSONResult.ok("头像上传成功");
  }

  private void updateUserCookie(HttpServletRequest req, HttpServletResponse rep, @RequestParam("userId") @ApiParam(name = "userId", value = "用户id", required = true) String userId) {
    // 5 更新cookie
    String token = redisOperator.get(RedisKey.USERTOKEN.getKey() + ":" + userId);
    Users user = usersService.queryUserInfo(userId);
    UsersVO usersVO = new UsersVO();
    BeanUtils.copyProperties(user, usersVO);
    usersVO.setUserToken(token);
    CookieUtils.setCookie(req, rep, "user", JsonUtils.objectToJson(usersVO), true);
  }


  @ApiOperation(value = "用户中心我的订单", notes = "根据状态查询订单", httpMethod = "POST")
  @PostMapping("/orders")
  public JSONResult getOrdersByStatus(
          @ApiParam(name = "userId", value = "用户id", required = true)
          @RequestParam("userId") String userId,
          @ApiParam(name = "orderStatus", value = "订单状态", required = true)
          @RequestParam("orderStatus") Integer orderStatus,
          @ApiParam(name = "page", value = "当前页", required = true)
          @RequestParam("page") Integer page,
          @ApiParam(name = "pageSize", value = "每页条数", required = true)
          @RequestParam("pageSize") Integer pageSize
  ) {
    if(StringUtils.isBlank(userId)) {
      return JSONResult.errMsg("用户id不能为空");
    }
    PagedGridResult<UserCenterOrderVO> grid = ordersService.queryOrdersByStatus(userId, orderStatus, page, pageSize);
    return JSONResult.ok(grid);
  }

  @ApiOperation(value = "删除订单", notes = "删除订单", httpMethod = "DELETE")
  @DeleteMapping("/orders")
  public JSONResult deleteOrder(
          @ApiParam(name = "userId", value = "用户id", required = true)
          @RequestParam("userId") String userId,
          @ApiParam(name = "orderId", value = "订单id", required = true)
          @RequestParam("orderId") String orderId
  ) {
    if(StringUtils.isBlank(userId)) {
      return JSONResult.errMsg("用户id不能为空");
    }
    if(StringUtils.isBlank(orderId)) {
      return JSONResult.errMsg("订单id不能为空");
    }

    JSONResult result = ordersService.checkOrder(userId, orderId);
    if(result.getStatus() != HttpStatus.OK.value()) {
      return result;
    }

    ordersService.deleteOrder(userId, orderId);
    return result;
  }

  /**
   * 检查用户更新提交的用户信息是否正确
   * @param updatedUserBO
   * @return
   */
  public JSONResult checkUpdatedUserInfo(UpdatedUserBO updatedUserBO) {
    String nickname = updatedUserBO.getNickname();
    String realname = updatedUserBO.getRealname();
    String mobile = updatedUserBO.getMobile();
    String email = updatedUserBO.getEmail();

    if(StringUtils.isBlank(nickname)) {
      return JSONResult.errMsg("昵称不能为空");
    }
    if(nickname.length() > 12) {
      return JSONResult.errMsg("昵称长度不能超过12位");
    }
    if(StringUtils.isNotBlank(realname) && realname.length() > 12) {
      return JSONResult.errMsg("真实姓名长度不能超过12位");
    }

    if(StringUtils.isNotBlank(mobile) && mobile.length() != 11) {
      return JSONResult.errMsg("手机长度不是11位");
    }

    if(StringUtils.isNotBlank(mobile) && !ValidateUtils.checkMobileIsOk(mobile)) {
      return JSONResult.errMsg("请输入有效的手机号");
    }

    if(StringUtils.isNotBlank(email) && !ValidateUtils.checkEmailIsOk(email)) {
      return JSONResult.errMsg("请输入有效的邮箱");
    }

    return JSONResult.ok();
  }

  @ApiOperation(value = "用户中心-订单动向", notes = "用户中心-订单动向", httpMethod = "GET")
  @GetMapping("/trend")
  public JSONResult getItemsByOrderId(
          @ApiParam(name = "用户id", value = "用户id", required = true)
          @RequestParam("userId") String userId,
          @ApiParam(name = "page", value = "当前页", required = true)
          @RequestParam("page") Integer page,
          @ApiParam(name = "pageSize", value = "每页条数", required = true)
          @RequestParam("pageSize") Integer pageSize
  ) {
    if(StringUtils.isBlank(userId)) {
      return JSONResult.errMsg("用户id为空");
    }

    PagedGridResult<OrderStatus> grid = ordersService.getOrdersTrend(userId, page, pageSize);
    return JSONResult.ok(grid);
  }
}
