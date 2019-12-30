package com.zmall.controller.backend;

import com.zmall.common.ServerResponse;
import com.zmall.service.ICategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @ClassName: CategoryManagerController
 * @Date 2019-09-15 16:20
 * @Author duanxin
 **/
@Api(tags = "category-manager")
@RestController
@RequestMapping("/manager/category/")
public class CategoryManagerController {

    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 添加分类
     *
     * @param categoryName
     * @param parentId
     * @return
     */
    @ApiOperation(value = "添加分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryName", value = "分类名称", required = true, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "parentId", value = "分类父亲类别id（例如：电子产品：1，手机：2，手机的parentId=1）", required = true, paramType = "query", dataType = "int")
    })
    @RequestMapping(value = "add_category.do", method = RequestMethod.POST)
    public ServerResponse addCategory(String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
        //全部通过拦截器验证是否登录以及权限
        return iCategoryService.addCategory(categoryName, parentId);

    }

    /**
     * 更新分类名称
     *
     * @param categoryId
     * @param categoryName
     * @return
     */
    @ApiOperation(value = "更新分类名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "分类id", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "categoryName", value = "分类名称", required = true, paramType = "query", dataType = "string")
    })
    @RequestMapping(value = "set_category_name.do", method = RequestMethod.POST)
    public ServerResponse setCategoryName(Integer categoryId, String categoryName) {
        //全部通过拦截器验证是否登录以及权限
        return iCategoryService.updateCategoryName(categoryId, categoryName);
    }

    /**
     * 查询某分类的子分类
     *
     * @param categoryId
     * @return
     */
    @ApiOperation(value = "查询某分类的子分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "分类id", required = true, paramType = "query", dataType = "int"),
    })
    @RequestMapping(value = "get_category.do", method = RequestMethod.GET)
    public ServerResponse getChildrenParallelCategory(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        //全部通过拦截器验证是否登录以及权限
        return iCategoryService.getChildrenParallelCategory(categoryId);
    }

    /**
     * 查询某分类的子分类（包括子孙）
     *
     * @param categoryId
     * @return
     */
    @ApiOperation(value = "查询某分类的子分类（包括子孙）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "分类id", required = true, paramType = "query", dataType = "int"),
    })
    @RequestMapping(value = "get_deep_category.do", method = RequestMethod.GET)
    public ServerResponse getCategoryAndDeepChildrenCategory(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        //全部通过拦截器验证是否登录以及权限
        return iCategoryService.selectCategoryAndChildrenById(categoryId);
    }


}
