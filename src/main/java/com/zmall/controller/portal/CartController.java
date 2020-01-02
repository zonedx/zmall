package com.zmall.controller.portal;

import com.zmall.common.Const;
import com.zmall.common.ServerResponse;
import com.zmall.controller.common.CurrentUser;
import com.zmall.controller.common.Login;
import com.zmall.pojo.User;
import com.zmall.service.ICartService;
import com.zmall.util.CookieUtils;
import com.zmall.util.JsonUtils;
import com.zmall.util.RedisPoolUtils;
import com.zmall.vo.CartVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: CartController
 * @Date 2019-09-19 23:30
 * @Author duanxin
 **/
@Api(tags = {"cart"})
@Controller
@RequestMapping("/cart/")
public class CartController {

    private ICartService iCartService;

    @Autowired
    public CartController(ICartService iCartService) {
        this.iCartService = iCartService;
    }

    @Login
    @ApiOperation(value = "查看购物车")
    @GetMapping("list.do")
    @ResponseBody
    public ServerResponse<CartVo> list(@CurrentUser User user) {
        return iCartService.list(user.getId());
    }

    @Login
    @ApiOperation(value = "加入购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "count", value = "商品数量", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "productId", value = "商品编号", required = true, paramType = "query", dataType = "int")
    })
    @PostMapping("add.do")
    @ResponseBody
    public ServerResponse<CartVo> add(@CurrentUser User user, Integer count, Integer productId) {
        return iCartService.add(user.getId(), productId, count);
    }

    @Login
    @ApiOperation(value = "更新购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "count", value = "商品数量", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "productId", value = "商品编号", required = true, paramType = "query", dataType = "int")
    })
    @PostMapping("update.do")
    @ResponseBody
    public ServerResponse update(@CurrentUser User user, Integer count, Integer productId) {
        return iCartService.update(user.getId(), productId, count);
    }

    @Login
    @ApiOperation(value = "移除购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productIds", value = "商品编号[xx,xx]", required = true, paramType = "query", dataType = "string")
    })
    @PostMapping("delete_product.do")
    @ResponseBody
    public ServerResponse deleteProduct(@CurrentUser User user, String productIds) {
        return iCartService.deleteProduct(user.getId(), productIds);
    }

    /**
     * 全选  |  全反选
     */
    @Login
    @ApiOperation(value = "购物车全选")
    @PostMapping("select_all.do")
    @ResponseBody
    public ServerResponse<CartVo> selectAll(@CurrentUser User user) {
        return iCartService.selectOrUnSelect(user.getId(), null, Const.Cart.CHECKED);
    }

    @Login
    @ApiOperation(value = "购物车全反选")
    @PostMapping("un_select_all.do")
    @ResponseBody
    public ServerResponse<CartVo> unSelectAll(@CurrentUser User user) {
        return iCartService.selectOrUnSelect(user.getId(), null, Const.Cart.UN_CHECKED);
    }

    /**
     * 单独选中 | 单独反选
     */
    @Login
    @ApiOperation(value = "单独选中")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "商品编号", required = true, paramType = "query", dataType = "int")
    })
    @PostMapping("select.do")
    @ResponseBody
    public ServerResponse<CartVo> select(Integer productId, @CurrentUser User user) {
        return iCartService.selectOrUnSelect(user.getId(), productId, Const.Cart.CHECKED);
    }

    @Login
    @ApiOperation(value = "单独反选")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "商品编号", required = true, paramType = "query", dataType = "int")
    })
    @PostMapping("un_select.do")
    @ResponseBody
    public ServerResponse<CartVo> unSelect(Integer productId,@CurrentUser User user) {
        return iCartService.selectOrUnSelect(user.getId(), productId, Const.Cart.UN_CHECKED);
    }


    /**
     * 查询当前用户购物车里面的产品数量，如果一个产品有10个，那么数量就是10
     * 下方注释表示特殊处理也交给拦截器处理
     */
    @ApiOperation(value = "查看购物车数量")
    @GetMapping("get_cart_product_count.do")
    @ResponseBody
    public ServerResponse<Integer> getCartProductCount(@CurrentUser User user) {
        if (user == null){
            return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
        }
        return iCartService.getCartProductCount(user.getId());
    }

//    /**
//     * 查询当前用户购物车里面的产品数量，如果一个产品有10个，那么数量就是10
//     * 此方法和 {@link UserController}中的getUserInfo()方法需要特殊处理
//     *   返回未登录信息即可，不必要强制登录
//     */
//    @ApiOperation(value = "查看购物车数量")
//    @GetMapping("get_cart_product_count.do")
//    @ResponseBody
//    public ServerResponse<Integer> getCartProductCount(HttpServletRequest request) {
//        String loginToken = CookieUtils.readLoginToken(request);
//        User user = new User();
//        if (StringUtils.isNotEmpty(loginToken)){
//            String userJson = RedisPoolUtils.get(loginToken);
//            user = JsonUtils.string2Obj(userJson, User.class);
//        }
//        if (user == null){
//            return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
//        }
//        return iCartService.getCartProductCount(user.getId());
//    }
}
