package com.icoding.controller;

import com.icoding.bo.UpdatedUserBO;
import com.icoding.pojo.Users;
import com.icoding.service.UsersService;
import com.icoding.utils.JSONResult;
import com.icoding.utils.ValidateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "用户中心", tags = {"用户中心相关接口"})
@RestController
@RequestMapping("/center")
public class UserCenterController {

  @Autowired
  UsersService usersService;

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
      return JSONResult.ok("更新成功");
    } else {
      return result;
    }
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
    if(!ValidateUtils.checkMobileIsOk(mobile)) {
      return JSONResult.errMsg("请输入有效的手机号");
    }

    if(StringUtils.isNotBlank(email) && !ValidateUtils.checkEmailIsOk(email)) {
      return JSONResult.errMsg("请输入有效的邮箱");
    }

    return JSONResult.ok();
  }
}
