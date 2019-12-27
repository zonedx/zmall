package com.zmall.controller.portal;

import com.zmall.common.Const;
import com.zmall.common.ServerResponse;
import com.zmall.controller.common.CurrentUser;
import com.zmall.controller.common.Login;
import com.zmall.pojo.User;
import com.zmall.service.ICartService;
import com.zmall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: CartController
 * @Date 2019-09-19 23:30
 * @Author duanxin
 **/
@Controller
@RequestMapping("/cart/")
public class CartController {

    @Autowired
    private ICartService iCartService;

    @RequestMapping("list.do")
    @ResponseBody
    @Login
    public ServerResponse<CartVo> list(@CurrentUser User user) {
        return iCartService.list(user.getId());
    }

    @RequestMapping("add.do")
    @ResponseBody
    @Login
    public ServerResponse<CartVo> add(@CurrentUser User user, Integer count, Integer productId) {
        return iCartService.add(user.getId(), productId, count);
    }

    @RequestMapping("update.do")
    @ResponseBody
    @Login
    public ServerResponse update(@CurrentUser User user, Integer count, Integer productId) {
        return iCartService.update(user.getId(), productId, count);
    }

    @RequestMapping("delete_product.do")
    @ResponseBody
    @Login
    public ServerResponse deleteProduct(@CurrentUser User user, String productIds) {
        return iCartService.deleteProduct(user.getId(), productIds);
    }

    //全选
    //全反选

    @RequestMapping("select_all.do")
    @ResponseBody
    @Login
    public ServerResponse<CartVo> selectAll(@CurrentUser User user) {
        return iCartService.selectOrUnSelect(user.getId(), null, Const.Cart.CHECKED);
    }

    @RequestMapping("un_select_all.do")
    @ResponseBody
    @Login
    public ServerResponse<CartVo> unSelectAll(@CurrentUser User user) {
        return iCartService.selectOrUnSelect(user.getId(), null, Const.Cart.UN_CHECKED);
    }

    //单独选
    //单独反选

    @RequestMapping("select.do")
    @ResponseBody
    @Login
    public ServerResponse<CartVo> select(Integer productId, @CurrentUser User user) {
        return iCartService.selectOrUnSelect(user.getId(), productId, Const.Cart.CHECKED);
    }

    @RequestMapping("un_select.do")
    @ResponseBody
    @Login
    public ServerResponse<CartVo> unSelect(Integer productId, User user) {
        return iCartService.selectOrUnSelect(user.getId(), productId, Const.Cart.UN_CHECKED);
    }

    //查询当前用户购物车里面的产品数量，如果一个产品有10个，那么数量就是10

    @RequestMapping("get_cart_product_count.do")
    @ResponseBody
    @Login
    public ServerResponse<Integer> getCartProductCount(@CurrentUser User user) {
        return iCartService.getCartProductCount(user.getId());

    }
}
