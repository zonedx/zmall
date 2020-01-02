package com.zmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.zmall.common.ServerResponse;
import com.zmall.service.IOrderService;
import com.zmall.vo.OrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: OrderManageController
 * @Date 2019-09-23 00:54
 * @Author duanxin
 **/
@Api(tags = "order-manager")
@RestController
@RequestMapping("/manage/order/")
public class OrderManagerController {

    private IOrderService iOrderService;

    @Autowired
    public OrderManagerController(IOrderService iOrderService) {
        this.iOrderService = iOrderService;
    }

    @ApiOperation(value = "订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页，默认1", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数量，默认10", paramType = "query", dataType = "int")
    })
    @RequestMapping(value = "list.do", method = RequestMethod.GET)
    public ServerResponse<PageInfo> orderList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        //全部通过拦截器验证是否登录以及权限
        return iOrderService.manageList(pageNum, pageSize);
    }

    @ApiOperation(value = "订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "订单编号", required = true, paramType = "query", dataType = "long")
    })
    @RequestMapping(value = "detail.do", method = RequestMethod.GET)
    public ServerResponse<OrderVo> orderDetail(Long orderNo) {
        //全部通过拦截器验证是否登录以及权限
        return iOrderService.manageDetail(orderNo);
    }

    @ApiOperation(value = "查询订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "订单编号", required = true, paramType = "query", dataType = "long"),
            @ApiImplicitParam(name = "pageNum", value = "当前页，默认1", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数量，默认10", paramType = "query", dataType = "int")
    })
    @RequestMapping(value = "search.do", method = RequestMethod.GET)
    public ServerResponse<PageInfo> orderSearch(Long orderNo, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        //全部通过拦截器验证是否登录以及权限
        return iOrderService.manageSearch(orderNo, pageNum, pageSize);
    }

    @ApiOperation(value = "发货")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "订单编号", required = true, paramType = "query", dataType = "long")
    })
    @RequestMapping(value = "send_goods.do", method = RequestMethod.GET)
    public ServerResponse<String> orderSendGoods(Long orderNo) {
        //全部通过拦截器验证是否登录以及权限
        return iOrderService.manageSendGoods(orderNo);
    }
}
