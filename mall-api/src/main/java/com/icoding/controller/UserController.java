package com.icoding.controller;

import com.icoding.bo.ShopcartItemBO;
import com.icoding.bo.UserBO;
import com.icoding.enums.RedisKey;
import com.icoding.pojo.Users;
import com.icoding.service.UsersService;
import com.icoding.utils.*;
import com.icoding.vo.UsersVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.icoding.enums.RedisKey.SHOPCART;

@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("/user")
public class UserController {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UsersService usersService;

  @Autowired
  private RedisOperator redisOperator;

  @ApiIgnore
  @GetMapping("/hello")
  public String hello() {
    LOGGER.debug("hello world ====>");
    LOGGER.info("hello world ====>");
    LOGGER.warn("hello world ====>");
    LOGGER.error("hello world ====>");
    return "hello";
  }

  @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
  @GetMapping("usernameIsExist")
  public JSONResult usernameIsExist(@RequestParam String username) {
    // 1 用户名是否为空
    if(StringUtils.isBlank(username)) {
      return JSONResult.errMsg("用户名为空");
    }

    Users current = usersService.queryIsUserExists(username);
    // 2 用户名是否存在
    if(current != null) {
      return JSONResult.errMsg("用户名已存在");
    }

    // 3 请求成功
    return JSONResult.ok(current);
  }

  @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
  @PostMapping("/regist")
  public JSONResult regist(@RequestBody UserBO userBO, HttpServletRequest req, HttpServletResponse rep) {
    String username = userBO.getUsername();
    String password = userBO.getPassword();
    String confirmPassword = userBO.getConfirmPassword();

    // 0 判断用户名和密码不能为空
    if(StringUtils.isBlank(username) ||
        StringUtils.isBlank(password) ||
        StringUtils.isBlank(confirmPassword)) {
      return JSONResult.errMsg("用户名或密码为空");
    }
    // 1 判断用户名是否存在
    if(usersService.queryIsUserExists(username) != null) {
      return JSONResult.errMsg("用户名已存在");
    }
    // 2 判断密码长度是否小于6为
    if(password.length() < 6) {
      return JSONResult.errMsg("用户密码长度不能小于6位");
    }
    // 3 判断用密码和确认密码是否相同
    if(!password.equals(confirmPassword)) {
      return JSONResult.errMsg("两次输入密码不相同");
    }
    // 4 执行注册
    Users user = usersService.createUser(userBO);

    // 5 生成usersVO 并保存用户会话到redis中
    UsersVO usersVO = convertToUsersVO(user);

    // 6 设置cookie
    CookieUtils.setCookie(req, rep, "user", JsonUtils.objectToJson(usersVO), true);

    // 同步购物车数据(多端数据同步)
    synchShopcartData(req, rep, usersVO.getId());

    return JSONResult.ok(usersVO);
  }

  /**
   * 根据Users生成UsersVO, 并保存用户会话token到redis中
   * @param user
   * @return
   */
  private UsersVO convertToUsersVO(Users user) {
    // 生成用户token, 存入redis中
    String uniqueToken = UUID.randomUUID().toString().trim();
    redisOperator.set(RedisKey.USERTOKEN.getKey() + ":" + user.getId(), uniqueToken);

    UsersVO usersVO = new UsersVO();
    BeanUtils.copyProperties(user, usersVO);
    usersVO.setUserToken(uniqueToken);
    return usersVO;
  }

  @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
  @PostMapping("/login")
  public JSONResult login(@RequestBody UserBO userBO, HttpServletRequest req, HttpServletResponse rep) throws Exception {
    String username = userBO.getUsername();
    String password = userBO.getPassword();
     // 0 用户名和密码不能为空
    if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
      return JSONResult.errMsg("用户名或密码为空");
    }
     // 1 密码长度不能少于6位
    if(password.length() < 6) {
      return JSONResult.errMsg("用户密码长度不能小于6位");
    }
     // 2 是否有匹配用户
    Users user = usersService.queryUserForLogin(username, MD5Utils.getMD5Str(userBO.getPassword()));
    if(user == null) {
      return JSONResult.errMsg("用户名或密码错误");
    }

    // 生成用户token, 存入redis中
    UsersVO usersVO = convertToUsersVO(user);

    // 4 设置cookie
    CookieUtils.setCookie(req, rep, "user", JsonUtils.objectToJson(usersVO), true);

    // 同步购物车数据(多端数据同步)
    synchShopcartData(req, rep, usersVO.getId());

    // 5 返回登录信息
    return JSONResult.ok(usersVO);
  }

  @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "GET")
  @GetMapping("/logout")
  public JSONResult logout(@RequestParam String userId, HttpServletRequest req, HttpServletResponse rep) {
    // 用户退出登录，清空user信息
    CookieUtils.deleteCookie(req, rep, "user");

    // 用户退出登录，清空购物车
    CookieUtils.deleteCookie(req, rep, SHOPCART.getKey());

    // 分布式会话中需要清除用户数据
    redisOperator.del(RedisKey.USERTOKEN.getKey() + ":" + userId);

    return JSONResult.ok();
  }


  /**
   * 注册成功和登录成功后需要同步cookie和redis中的购物车数据
   */
  private void synchShopcartData(HttpServletRequest req, HttpServletResponse rep, String userId) {
    /**
     * 1. redis中没数据，如果cookie中的购物车为空，那么这个时候不做任何处理
     *                 如果cookie中的购物车不为空，直接放入购物车中
     *
     * 2. redis中有数据，如果cookie中的购物车为空，直接把redis的购物车覆盖到本地cookie
     *                 如果cookie中的购物车不为空，以前端cookie数据为准
     *
     * 3. 同步到redis后，覆盖本地cookie购物车中的数据，保证本地购物车中数据是同步的
     */

    // cookie中的数据
    String shopcartCookieStr = CookieUtils.getCookieValue(req, SHOPCART.getKey(), true);

    // redis中的数据
    String key = SHOPCART.getKey() + ":" + userId;
    String shopcartRedisStr = redisOperator.get(key);

    if(StringUtils.isBlank(shopcartRedisStr)) {
      // redis为空，cookie不为空，直接把cookie放入redis中
      if(StringUtils.isNotBlank(shopcartCookieStr)) {
        redisOperator.set(key, shopcartCookieStr);
      }
    } else {
      // redis不为空, cookie为空，直接把redis放入cookie中
      if(StringUtils.isBlank(shopcartCookieStr)) {
        CookieUtils.setCookie(req, rep, SHOPCART.getKey(), shopcartRedisStr, true);
      } else {
        //  redis不为空，cookie不为空，合并cookie和redis中的数据(重复商品则以cookie为主，覆盖redis)
        List<ShopcartItemBO> shopcartCookieItems = JsonUtils.jsonToList(shopcartCookieStr, ShopcartItemBO.class);
        List<ShopcartItemBO> shopcartRedisItems = JsonUtils.jsonToList(shopcartRedisStr, ShopcartItemBO.class);

        // 存放redis和cookie中重合的商品，后续待删除
        List<ShopcartItemBO> pendingDeleteList = new ArrayList<>();
        for(ShopcartItemBO redisItem : shopcartRedisItems) {
          String redisItemSpecId = redisItem.getSpecId();
          for(ShopcartItemBO cookieItem : shopcartCookieItems) {
            String cookieItemSpecId = cookieItem.getSpecId();
            if(redisItemSpecId.equals(cookieItemSpecId)) {
              // cookie与redis中包含重复商品, 以cookie中的商品数量为主
              redisItem.setBuyCounts(cookieItem.getBuyCounts());
              // 放入待删除列表中
              pendingDeleteList.add(cookieItem);
            }
          }
        }

        // 从cookie中删除重合的商品
        shopcartCookieItems.removeAll(pendingDeleteList);

        // 合并redis和cookie
        shopcartRedisItems.addAll(shopcartCookieItems);

        // 更新redis数据和cookie数据
        String newRedisShopcartItemsStr = JsonUtils.objectToJson(shopcartRedisItems);
        redisOperator.set(key, newRedisShopcartItemsStr);
        CookieUtils.setCookie(req, rep, SHOPCART.getKey(), newRedisShopcartItemsStr, true);
      }
    }
  }
}
