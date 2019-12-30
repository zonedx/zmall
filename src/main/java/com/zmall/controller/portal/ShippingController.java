package com.zmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.zmall.common.ServerResponse;
import com.zmall.controller.common.Login;
import com.zmall.pojo.Shipping;
import com.zmall.pojo.User;
import com.zmall.service.IShippingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ShippingController.class);

    @Autowired
    private IShippingService iShippingService;

    @ApiOperation(value = "新增收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shipping", value = "shipping对象", required = true, paramType = "query", dataType = "Shipping")
    })
    @PostMapping("add.do")
    public ServerResponse add(User user, Shipping shipping) {
        return iShippingService.add(user.getId(), shipping);
    }

    @ApiOperation(value = "删除收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shippingId", value = "地址编号", required = true, paramType = "query", dataType = "int")
    })
    @PostMapping("delete.do")
    public ServerResponse delete(User user, Integer shippingId) {
        return iShippingService.delete(user.getId(), shippingId);
    }

    @ApiOperation(value = "更新收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shipping", value = "shipping对象", required = true, paramType = "query", dataType = "Shipping")
    })
    @PostMapping("update.do")
    public ServerResponse update(User user, Shipping shipping) {
        return iShippingService.update(user.getId(), shipping);
    }

    @ApiOperation(value = "选择收货地址")
    @GetMapping("select.do")
    public ServerResponse<Shipping> select(User user, Integer shippingId) {
        return iShippingService.select(user.getId(), shippingId);
    }

    @ApiOperation(value = "收货地址列表")
    @GetMapping("list.do")
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         User user) {
        return iShippingService.list(user.getId(), pageNum, pageSize);
    }
}
