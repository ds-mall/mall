package com.icoding.controller;

import com.icoding.enums.CategoryType;
import com.icoding.enums.YesOrNo;
import com.icoding.pojo.Carousel;
import com.icoding.pojo.Category;
import com.icoding.service.CarouselService;
import com.icoding.service.CategoryService;
import com.icoding.service.ItemsService;
import com.icoding.utils.JSONResult;
import com.icoding.utils.JsonUtils;
import com.icoding.utils.RedisOperator;
import com.icoding.vo.NewItemsCategoryVO;
import com.icoding.vo.SecondLevelCategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.icoding.enums.RedisKey.*;


@Api(value = "首页", tags = {"首页展示相关接口"})
@RestController
@RequestMapping("/index")
public class IndexController {
  private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

  @Autowired
  CarouselService carouselService;

  @Autowired
  CategoryService categoryService;

  @Autowired
  ItemsService itemsService;

  @Autowired
  RedisOperator redisOperator;

  @ApiOperation(value = "首页轮播图列表接口", notes = "首页轮播图列表", httpMethod = "GET")
  @Transactional(propagation = Propagation.SUPPORTS)
  @GetMapping("/carousel")
  public JSONResult queryAllCarousel() {
    List<Carousel> carousels;
    String carouselStr = redisOperator.get(CAROUSELS.getKey());
    if (StringUtils.isNotBlank(carouselStr)) {
      LOGGER.info("redis key carousels, values {}", carouselStr);
      carousels = JsonUtils.jsonToList(carouselStr, Carousel.class);
    } else {
      carousels = carouselService.queryAll(YesOrNo.YES.getType());
      redisOperator.set(CAROUSELS.getKey(), JsonUtils.objectToJson(carousels));
    }
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
    List<Category> categories;
    String categoryStr = redisOperator.get(CATEGORIES.getKey());
    if(StringUtils.isNotBlank(categoryStr)) {
      LOGGER.info("redis key {}， values {}", CATEGORIES.getKey(), categoryStr);
      categories = JsonUtils.jsonToList(categoryStr, Category.class);
    } else {
      categories = categoryService.queryCategoryByType(CategoryType.ONE.getType());
      redisOperator.set(CATEGORIES.getKey(), JsonUtils.objectToJson(categories));
    }
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
    List<SecondLevelCategoryVO> subCategories;
    String key = SUBCATEGORIES.getKey();
    String field = rootCatId.toString();

    String subCategoriesStr = redisOperator.hget(key, field);
    if(StringUtils.isNotBlank(subCategoriesStr)) {
      subCategories = JsonUtils.jsonToList(subCategoriesStr, SecondLevelCategoryVO.class);
    } else {
      subCategories = categoryService.queryCategoryByFatherId(rootCatId);

      if (subCategories != null && subCategories.size() > 0) {
        redisOperator.hset(key, field, JsonUtils.objectToJson(subCategories));
      } else {
        /**
         * 查询的key在redis中不存在
         * 对应的id在数据库中也不存在
         * 此时被非法用户进行攻击，大量的请求会直接打在db上，造成宕机，从而影响整个系统
         * 这种现象称为: 缓存穿透
         *
         * 解决方案: 把空的数据也缓存起来， 比如空字符串，空对象，空数组或list
          */
        redisOperator.hset(key, field, JsonUtils.objectToJson(subCategories), 5 * 60);
      }
    }
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
