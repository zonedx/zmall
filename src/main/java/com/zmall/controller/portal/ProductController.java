package com.zmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.zmall.common.ServerResponse;
import com.zmall.service.IProductService;
import com.zmall.vo.ProductDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: ProductController
 * @Date 2019-09-18 00:26
 * @Author duanxin
 **/

@Api(tags = {"product"})
@RestController
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    @ApiOperation(value = "商品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "商品编号", required = true, paramType = "query", dataType = "int")
    })
    @GetMapping("detail.do")
    public ServerResponse<ProductDetailVo> detail(Integer productId) {
        return iProductService.getProductDetail(productId);
    }

    @ApiOperation(value = "商品详情(restful风格接口)")
    @RequestMapping(value = "/{productId}",method = RequestMethod.GET)
    public ServerResponse<ProductDetailVo> detailRestful(@PathVariable Integer productId) {
        return iProductService.getProductDetail(productId);
    }

    @ApiOperation(value = "商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "模糊查询商品", paramType = "query",dataType = "string"),
            @ApiImplicitParam(name = "categoryId", value = "商品类别id", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageNum", value = "当前页，默认1", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数量，默认10", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "orderBy", value = "根据价格升序或降序排列(格式：price_asc|price_desc)", paramType = "query", dataType = "string"),
    })
    @GetMapping("list.do")
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword", required = false) String keyword,
                                         @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         @RequestParam(value = "orderBy",defaultValue = "")String orderBy) {
        return iProductService.getProductByKeywordCategory(keyword,categoryId,pageNum,pageSize,orderBy);
    }

    /**
     * http://www.dlzonemall.com/product/list/手机/100002/1/10/price_asc/
     * @param keyword
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    @ApiOperation(value = "商品列表(restful风格接口)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "模糊查询商品", paramType = "path", dataType = "string"),
            @ApiImplicitParam(name = "categoryId", value = "商品类别id", paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "pageNum", value = "当前页，默认1", paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数量，默认10", paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "orderBy", value = "根据价格升序或降序排列(格式：price_asc|price_desc)", paramType = "path", dataType = "string"),
    })
    @RequestMapping(value = "/list/{keyword}/{categoryId}/{pageNum}/{pageSize}/{orderBy}",method = RequestMethod.GET)
    public ServerResponse<PageInfo> listRestful(@PathVariable(value = "keyword") String keyword,
                                         @PathVariable(value = "categoryId") Integer categoryId,
                                         @PathVariable(value = "pageNum") Integer pageNum,
                                         @PathVariable(value = "pageSize") Integer pageSize,
                                         @PathVariable(value = "orderBy")String orderBy) {
        if (pageNum == null){
            pageNum = 1;
        }
        if (pageSize == null){
            pageSize = 10;
        }
        if (StringUtils.isBlank(orderBy)){
            orderBy = "price_asc";
        }
        return iProductService.getProductByKeywordCategory(keyword,categoryId,pageNum,pageSize,orderBy);
    }

    /**
     * http://www.dlzonemall.com/product/keyword/手机/1/10/price_asc/
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    @ApiOperation(value = "商品列表(restful风格接口)")
    @RequestMapping(value = "/keyword/{keyword}/{pageNum}/{pageSize}/{orderBy}",method = RequestMethod.GET)
    public ServerResponse<PageInfo> listRestful(@PathVariable(value = "keyword") String keyword,
                                                @PathVariable(value = "pageNum") Integer pageNum,
                                                @PathVariable(value = "pageSize") Integer pageSize,
                                                @PathVariable(value = "orderBy")String orderBy) {
        if (pageNum == null){
            pageNum = 1;
        }
        if (pageSize == null){
            pageSize = 10;
        }
        if (StringUtils.isBlank(orderBy)){
            orderBy = "price_asc";
        }
        return iProductService.getProductByKeywordCategory(keyword,null,pageNum,pageSize,orderBy);
    }


    /**
     * http://www.dlzonemall.com/product/categoryId/100002/1/10/price_asc/
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    @ApiOperation(value = "商品列表(restful风格接口)")
    @RequestMapping(value = "/categoryId/{categoryId}/{pageNum}/{pageSize}/{orderBy}",method = RequestMethod.GET)
    public ServerResponse<PageInfo> listRestful(@PathVariable(value = "categoryId") Integer categoryId,
                                                @PathVariable(value = "pageNum") Integer pageNum,
                                                @PathVariable(value = "pageSize") Integer pageSize,
                                                @PathVariable(value = "orderBy")String orderBy) {
        if (pageNum == null){
            pageNum = 1;
        }
        if (pageSize == null){
            pageSize = 10;
        }
        if (StringUtils.isBlank(orderBy)){
            orderBy = "price_asc";
        }
        return iProductService.getProductByKeywordCategory("",categoryId,pageNum,pageSize,orderBy);
    }
}
