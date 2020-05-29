package com.icoding.controller;

import com.icoding.enums.CategoryType;
import com.icoding.enums.YesOrNo;
import com.icoding.pojo.Carousel;
import com.icoding.pojo.Category;
import com.icoding.service.CarouselService;
import com.icoding.service.CategoryService;
import com.icoding.service.ItemsService;
import com.icoding.utils.JSONResult;
import com.icoding.vo.NewItemsCategoryVO;
import com.icoding.vo.SecondLevelCategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(value = "首页", tags = {"首页展示相关接口"})
@RestController
@RequestMapping("/index")
public class IndexController {

  @Autowired
  CarouselService carouselService;

  @Autowired
  CategoryService categoryService;

  @Autowired
  ItemsService itemsService;

  @ApiOperation(value = "首页轮播图列表接口", notes = "首页轮播图列表", httpMethod = "GET")
  @Transactional(propagation = Propagation.SUPPORTS)
  @GetMapping("/carousel")
  public JSONResult queryAllCarousel() {
    List<Carousel> carousels = carouselService.queryAll(YesOrNo.YES.getType());
    return JSONResult.ok(carousels);
  }

  /**
   * 首页分类展示需求
   * 1. 第一次刷新主页查询一级分类, 渲染到首页
   * 2. 鼠标上移到一级分类, 则加载其子分类内容，如果已经存在子分类， 则不需要加载(懒加载)
   */
  @ApiOperation(value = "一级分类接口", notes = "一级分类接口", httpMethod = "GET")
  @Transactional(propagation = Propagation.SUPPORTS)
  @GetMapping("/cats")
  public JSONResult queryCategory() {
    List<Category> categories = categoryService.queryCategoryByType(CategoryType.ONE.getType());
    return JSONResult.ok(categories);
  }

  @ApiOperation(value = "子分类接口", notes = "子分类接口", httpMethod = "GET")
  @Transactional(propagation = Propagation.SUPPORTS)
  @GetMapping("/subCat/{rootCatId}")
  public JSONResult querySubCategory(
          @ApiParam(name = "rootCatId", value = "一级分类ID", required = true)
          @PathVariable("rootCatId") Integer rootCatId) {
    if(rootCatId == null) {
      return JSONResult.errMsg("分类不存在");
    }
    List<SecondLevelCategoryVO> subCategories = categoryService.queryCategoryByFatherId(rootCatId);
    return JSONResult.ok(subCategories);
  }


  @ApiOperation(value = "推荐商品", notes = "首页各一级分类最新六款商品", httpMethod = "GET")
  @Transactional(propagation = Propagation.SUPPORTS)
  @GetMapping("/sixNewItems/{rootCatId}")
  public JSONResult querySixNewItems(
          @ApiParam(name = "rootCatId", value = "一级分类ID", required = true)
          @PathVariable("rootCatId") Integer rootCatId) {
    if(rootCatId == null) {
      return JSONResult.errMsg("分类不存在");
    }
    List<NewItemsCategoryVO> items = itemsService.queryItemsByCategory(rootCatId);
    return JSONResult.ok(items);
  }
}
