package com.icoding.controller;

import com.icoding.bo.UserAddressBO;
import com.icoding.enums.CategoryType;
import com.icoding.enums.YesOrNo;
import com.icoding.pojo.Carousel;
import com.icoding.pojo.Category;
import com.icoding.service.AddressService;
import com.icoding.service.CarouselService;
import com.icoding.service.CategoryService;
import com.icoding.service.ItemsService;
import com.icoding.utils.JSONResult;
import com.icoding.utils.ValidateUtils;
import com.icoding.vo.NewItemsCategoryVO;
import com.icoding.vo.SecondLevelCategoryVO;
import com.icoding.vo.UserAddressVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(value = "地址", tags = {"地址模块相关接口"})
@RestController
@RequestMapping("/address")
public class AddressController {

  /**
   * 用户在订单确认页面，可以针对收货地址做如下操作
   * 1 查询用户所有收货地址列表
   * 2 新增收货地址
   * 3 删除收货地址
   * 4 修改收货地址
   * 5 设置默认收货地址
   */
  @Autowired
  AddressService addressService;

  @ApiOperation(value = "用户地址列表", notes = "用户地址列表", httpMethod = "GET")
  @GetMapping("/list")
  public JSONResult queryAllCarousel(
          @ApiParam(name = "userId", value = "用户id", required = true)
          @RequestParam String userId
  ) {
    if(StringUtils.isBlank(userId)) {
      return JSONResult.errMsg("用户id不能为空");
    }
    List<UserAddressVO> addressList = addressService.getAddressList(userId);
    return JSONResult.ok(addressList);
  }

  @ApiOperation(value = "新增用户地址", notes = "新增用户地址", httpMethod = "POST")
  @PostMapping("/add")
  public JSONResult add(
          @ApiParam(name = "新增地址信息", value = "新增地址信息", required = true)
          @RequestBody UserAddressBO userAddressBO
  ) {
    JSONResult jsonResult = validateAddressInfo(userAddressBO);
    if(jsonResult.getStatus() != HttpStatus.OK.value()) {
      return jsonResult;
    }
    addressService.add(userAddressBO);
    return JSONResult.ok("新增成功");
  }

  @ApiOperation(value = "删除用户地址", notes = "删除用户地址", httpMethod = "DELETE")
  @DeleteMapping("/del")
  public JSONResult del(
          @ApiParam(name = "用户id", value = "用户id", required = true)
          @RequestParam String userId,
          @ApiParam(name = "地址id", value = "地址id", required = true)
          @RequestParam String addressId
  ) {
    if(StringUtils.isBlank(userId)) {
      return JSONResult.errMsg("用户id不能为空");
    }
    if(StringUtils.isBlank(addressId)) {
      return JSONResult.errMsg("地址id不能为空");
    }
    addressService.del(userId, addressId);
    return JSONResult.ok("删除成功");
  }

  @ApiOperation(value = "修改用户地址", notes = "修改用户地址", httpMethod = "PUT")
  @PutMapping("/update")
  public JSONResult update(
          @ApiParam(name = "修改地址信息", value = "修改地址信息", required = true)
          @RequestBody UserAddressBO userAddressBO
  ) {
    JSONResult jsonResult = validateAddressInfo(userAddressBO);
    if(jsonResult.getStatus() != HttpStatus.OK.value()) {
      return jsonResult;
    }
    addressService.update(userAddressBO);
    return JSONResult.ok("修改成功");
  }

  @ApiOperation(value = "设置为默认地址", notes = "设置为默认地址", httpMethod = "POST")
  @PostMapping("/setDefault")
  public JSONResult setDefault(
          @ApiParam(name = "用户id", value = "用户id", required = true)
          @RequestParam String userId,
          @ApiParam(name = "地址id", value = "地址id", required = true)
          @RequestParam String addressId
  ) {
    addressService.setDefault(userId, addressId);
    return JSONResult.ok("设置默认成功");
  }

  /**
   * 校验地址信息
   * @param userAddressBO
   * @return
   */
  private JSONResult validateAddressInfo(UserAddressBO userAddressBO) {
    if(StringUtils.isBlank(userAddressBO.getReceiver())) {
      return JSONResult.errMsg("收货人姓名不能为空");
    }
    if(userAddressBO.getReceiver().length() > 12) {
      return JSONResult.errMsg("收货人姓名不能超过12个字符");
    }
    if(StringUtils.isBlank(userAddressBO.getMobile())) {
      return JSONResult.errMsg("收货人手机号不能为空");
    }
    if(userAddressBO.getReceiver().length() == 11) {
      return JSONResult.errMsg("收货人手机号应为11位");
    }
    if(!ValidateUtils.checkMobileIsOk(userAddressBO.getMobile())) {
      return JSONResult.errMsg("收货人手机号格式不正确");
    }
    return JSONResult.ok();
  }
}
