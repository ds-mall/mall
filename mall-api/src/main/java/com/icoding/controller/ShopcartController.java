package com.icoding.controller;

import com.icoding.bo.ShopcartItemBO;
import com.icoding.service.ShopcartService;
import com.icoding.utils.JSONResult;
import com.icoding.utils.JsonUtils;
import com.icoding.utils.RedisOperator;
import com.icoding.vo.ShopcartItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.icoding.enums.RedisKey.SHOPCART;

@Api(value = "购物车", tags = {"购物车相关接口"})
@RestController
@RequestMapping("/shopcart")
public class ShopcartController {
  private static final Logger LOGGER = LoggerFactory.getLogger(ShopcartController.class);
  @Autowired
  ShopcartService shopcartService;

  @Autowired
  RedisOperator redisOperator;

  @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
  @PostMapping("/add")
  public JSONResult add(
          @ApiParam(name = "userId", value = "用户id", required = true)
          @RequestParam("userId") String userId,
          @ApiParam(name = "shopcartItem", value = "购物车商品信息", required = true)
          @RequestBody ShopcartItemBO shopcartItemBO,
          HttpServletRequest req,
          HttpServletResponse rep
          ) {
    if(StringUtils.isBlank(userId)) {
      return JSONResult.errMsg("用户id不能为空");
    }

    LOGGER.info(shopcartItemBO.toString());

    // 前端用户在登录的情况下，添加商品到购物车，后端同步购物车到redis缓存
    String key = SHOPCART.getKey() + ":" + userId;
    String shopcartJson = redisOperator.get(key);

    List<ShopcartItemBO> shopcartItems = new ArrayList<>();
    if(StringUtils.isNotBlank(shopcartJson)) {
      // 购物车 非空
      shopcartItems = JsonUtils.jsonToList(shopcartJson, ShopcartItemBO.class);

      Optional<ShopcartItemBO> optional = Objects.requireNonNull(shopcartItems).stream()
              .filter(item -> item.getSpecId().equals(shopcartItemBO.getSpecId()))
              .findFirst();

      if(optional.isPresent()) {
        // 购物车中该规格已存在 数量累加
        ShopcartItemBO existsShopcartItems = optional.get();
        existsShopcartItems.setBuyCounts(existsShopcartItems.getBuyCounts() + shopcartItemBO.getBuyCounts());
      } else {
        // 购物车中该规格不存在
        shopcartItems.add(shopcartItemBO);
      }
    } else {
      // 购物车 空
      shopcartItems.add(shopcartItemBO);
    }

    redisOperator.set(key, JsonUtils.objectToJson(shopcartItems));

    return JSONResult.ok();
  }

  @ApiOperation(value = "从购物车中删除商品", notes = "从购物车中删除商品", httpMethod = "POST")
  @PostMapping("/del")
  public JSONResult del(
          @ApiParam(name = "userId", value = "用户id", required = true)
          @RequestParam("userId") String userId,
          @ApiParam(name = "itemSpecId", value = "删除商品的规格id", required = true)
          @RequestParam String itemSpecId,
          HttpServletRequest req,
          HttpServletResponse rep
  ) {
    if(StringUtils.isBlank(userId)) {
      return JSONResult.errMsg("用户id不能为空");
    }
    if(StringUtils.isBlank(itemSpecId)) {
      return JSONResult.errMsg("商品规格id不能为空");
    }

    // 前端用户在登录的情况下，删除购物车中指定商品，后端同步购物车到redis缓存
    String key = SHOPCART.getKey() + ":" + userId;
    String shopcartJson = redisOperator.get(key);
    if(StringUtils.isNotBlank(shopcartJson)) {
      List<ShopcartItemBO> shopcartItems = JsonUtils.jsonToList(shopcartJson, ShopcartItemBO.class);

      List<ShopcartItemBO> shopcartItemsAfterDel = Objects.requireNonNull(shopcartItems).stream().filter(item -> !item.getSpecId().equals(itemSpecId)).collect(Collectors.toList());

      redisOperator.set(key, JsonUtils.objectToJson(shopcartItemsAfterDel));
    }

    return JSONResult.ok();
  }

  @ApiOperation(value = "刷新购物车", notes = "刷新购物车商品数据", httpMethod = "GET")
  @GetMapping("/refresh")
  public JSONResult refresh(
          @ApiParam(name = "itemSpecIds", value = "购物车商品规格拼接字符串",example = "1001, 1002, 1003")
          @RequestParam("itemSpecIds") String itemSpecIds) {
    if(StringUtils.isBlank(itemSpecIds)) {
      return JSONResult.ok();
    }
    List<ShopcartItemVO> shopcartItems = shopcartService.refresh(itemSpecIds);
    return JSONResult.ok(shopcartItems);
  }
}
