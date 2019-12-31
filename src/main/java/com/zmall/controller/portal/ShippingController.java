package com.zmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.zmall.common.ServerResponse;
import com.zmall.controller.common.CurrentUser;
import com.zmall.controller.common.Login;
import com.zmall.pojo.Shipping;
import com.zmall.pojo.User;
import com.zmall.service.IShippingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: ShippingController
 * @Date 2019-09-20 13:13
 * @Author duanxin
 **/
@Api(tags = "shipping")
@RestController
@Login
@RequestMapping("/shipping/")
public class ShippingController {

    private IShippingService iShippingService;

    @Autowired
    public ShippingController(IShippingService iShippingService) {
        this.iShippingService = iShippingService;
    }

    @ApiOperation(value = "新增收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shipping", value = "shipping对象", required = true, paramType = "query", dataType = "Shipping")
    })
    @PostMapping("add.do")
    public ServerResponse add(@CurrentUser User user, Shipping shipping) {
        return iShippingService.add(user.getId(), shipping);
    }

    @ApiOperation(value = "删除收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shippingId", value = "地址编号", required = true, paramType = "query", dataType = "int")
    })
    @PostMapping("delete.do")
    public ServerResponse delete(@CurrentUser User user, Integer shippingId) {
        return iShippingService.delete(user.getId(), shippingId);
    }

    @ApiOperation(value = "更新收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shipping", value = "shipping对象", required = true, paramType = "query", dataType = "Shipping")
    })
    @PostMapping("update.do")
    public ServerResponse update(@CurrentUser User user, Shipping shipping) {
        return iShippingService.update(user.getId(), shipping);
    }

    @ApiOperation(value = "选择收货地址")
    @GetMapping("select.do")
    public ServerResponse<Shipping> select(@CurrentUser User user, Integer shippingId) {
        return iShippingService.select(user.getId(), shippingId);
    }

    @ApiOperation(value = "收货地址列表")
    @GetMapping("list.do")
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         @CurrentUser User user) {
        return iShippingService.list(user.getId(), pageNum, pageSize);
    }
}
