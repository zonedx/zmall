package com.zmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.zmall.common.ServerResponse;
import com.zmall.service.IProductService;
import com.zmall.service.impl.ProductServiceImpl;
import com.zmall.vo.ProductDetailVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: ProductController
 * @Date 2019-09-18 00:26
 * @Author duanxin
 **/

@Controller
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    private IProductService iProductService;


    /**
     * 商品详情
     * @param productId
     * @return ServerResponse<ProductDetailVo>
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<ProductDetailVo> detail(Integer productId) {
        return iProductService.getProductDetail(productId);
    }



    /**
     * 产品列表
     * @param keyword     搜索关键词
     * @param categoryId  商品种类
     * @param pageNum     当前页
     * @param pageSize    每页大小
     * @param orderBy     按价格升降序
     * @return            ServerResponse<PageInfo>   PageHelper封装后的info
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword", required = false) String keyword,
                                         @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         @RequestParam(value = "orderBy",defaultValue = "")String orderBy) {
        return iProductService.getProductByKeywordCategory(keyword,categoryId,pageNum,pageSize,orderBy);
    }
}
