package com.icoding.controller;

import com.icoding.bo.CommentOrderItemsBO;
import com.icoding.pojo.ItemsComments;
import com.icoding.service.CommentService;
import com.icoding.service.OrdersService;
import com.icoding.utils.JSONResult;
import com.icoding.utils.PagedGridResult;
import com.icoding.vo.UserCenterCommentVO;
import com.icoding.vo.UserCenterOrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(value = "评论", tags = {"评论相关接口"})
@RestController
@RequestMapping("/comments")
public class CommentsController {
  @Autowired
  Sid sid;

  @Autowired
  OrdersService ordersService;

  @Autowired
  CommentService commentService;

  @Transactional(propagation = Propagation.REQUIRED)
  @ApiOperation(value = "发表评论", notes = "发表评论", httpMethod = "POST")
  @PostMapping("")
  public JSONResult saveComments(
          @RequestParam("userId") String userId,
          @RequestParam("orderId") String orderId,
          @RequestBody List<CommentOrderItemsBO> commentList
  ) {
    JSONResult result = ordersService.checkOrder(userId, orderId);
    if(result.getStatus() != HttpStatus.OK.value()) {
      return result;
    }

    if(commentList == null || commentList.isEmpty() || commentList.size() == 0) {
      return JSONResult.errMsg("评论内容不能为空");
    }

    List<ItemsComments> list = commentList.stream().map(CommentOrderItemsBO::convertToPojo)
            .map(itemsComments -> {
              itemsComments.setId(sid.nextShort());
              itemsComments.setUserId(userId);
              return itemsComments;
            }).collect(Collectors.toList());

    int count = commentService.saveComment(list);
    if(count == list.size()) {
      // 修改订单is_comment为1
      ordersService.setOrderIsCommented(userId, orderId);
      return JSONResult.ok("评论保存成功");
    } else {
      return JSONResult.errMsg("评论保存失败");
    }
  }

  @ApiOperation(value = "获取用户评论列表", notes = "获取用户评论列表", httpMethod = "GET")
  @GetMapping("/list")
  public JSONResult getUserComments(
          @ApiParam(name = "userId", value = "用户id", required = true)
          @RequestParam("userId") String userId,
          @ApiParam(name = "page", value = "当前页", required = true)
          @RequestParam("page") Integer page,
          @ApiParam(name = "pageSize", value = "每页条数", required = true)
          @RequestParam("pageSize") Integer pageSize
  ) {
    if(StringUtils.isBlank(userId)) {
      return JSONResult.errMsg("用户id不能为空");
    }
    PagedGridResult<UserCenterCommentVO> grid = commentService.queryCommentsByUserId(userId, page, pageSize);
    return JSONResult.ok(grid);
  }
}
