package com.icoding.controller;

import com.icoding.pojo.Items;
import com.icoding.pojo.ItemsImg;
import com.icoding.pojo.ItemsParam;
import com.icoding.pojo.ItemsSpec;
import com.icoding.service.ItemsService;
import com.icoding.utils.JSONResult;
import com.icoding.utils.PagedGridResult;
import com.icoding.vo.ItemCommentLevelAndCountVO;
import com.icoding.vo.ItemCommentVO;
import com.icoding.vo.ItemInfoVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
  @Autowired
  ItemsService itemsService;

  @ApiOperation(value = "商品详情", notes = "商品详情", httpMethod = "GET")
  @GetMapping("/info/{itemId}")
  public JSONResult getItemInfo(
          @ApiParam(name = "itemId", value = "商品id", required = true)
          @PathVariable("itemId") String itemId) {
    if(StringUtils.isBlank(itemId)) {
      return JSONResult.errMsg("商品id不能为空");
    }

    Items item = itemsService.queryItemById(itemId);
    List<ItemsImg> itemImgList = itemsService.queryItemImgById(itemId);
    List<ItemsSpec> itemSpecList = itemsService.queryItemSpecById(itemId);
    ItemsParam itemParams = itemsService.queryItemParamById(itemId);
    ItemInfoVO itemInfoVO = new ItemInfoVO(item, itemImgList, itemSpecList, itemParams);
    return JSONResult.ok(itemInfoVO);
  }

  @ApiOperation(value = "商品评论等级与对应评论数量", notes = "商品评论等级与对应评论数量", httpMethod = "GET")
  @GetMapping("/commentLevel")
  public JSONResult getItemComentLevelAndCounts(
          @ApiParam(name = "itemId", value = "商品id", required = true)
          @RequestParam("itemId") String itemId) {
    if(StringUtils.isBlank(itemId)) {
      return JSONResult.errMsg("商品id不能为空");
    }
    ItemCommentLevelAndCountVO itemCommentLevelAndCountVO = itemsService.queryItemCommentLevelAndCount(itemId);
    return JSONResult.ok(itemCommentLevelAndCountVO);
  }

  @ApiOperation(value = "商品指定等级评论列表", notes = "商品指定等级评论列表", httpMethod = "GET")
  @GetMapping("/comments")
  public JSONResult getItemComent(
          @ApiParam(name = "itemId", value = "商品id", required = true)
          @RequestParam("itemId") String itemId,
          @ApiParam(name = "level", value = "评论等级", required = true)
          @RequestParam("level") Integer level,
          @ApiParam(name = "page", value = "当前页", required = true)
          @RequestParam("page") Integer page,
          @ApiParam(name = "pageSize", value = "每页条数", required = true)
          @RequestParam("pageSize") Integer pageSize
  ) {
    if(StringUtils.isBlank(itemId)) {
      return JSONResult.errMsg("商品id不能为空");
    }
    PagedGridResult<ItemCommentVO> grid = itemsService.queryItemComments(itemId, level, page, pageSize);
    return JSONResult.ok(grid);
  }
}
