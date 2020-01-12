package com.zmall.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.zmall.common.Const;
import com.zmall.common.ServerResponse;
import com.zmall.controller.common.CurrentUser;
import com.zmall.controller.common.Login;
import com.zmall.pojo.User;
import com.zmall.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName: OrderController
 * @Date 2019-09-21 19:26
 * @Author duanxin
 **/

@Api(tags = {"order"})
@RestController
@Login
@RequestMapping("/order/")
@Slf4j
public class OrderController {

    private IOrderService iOrderService;

    @Autowired
    public OrderController(IOrderService iOrderService) {
        this.iOrderService = iOrderService;
    }

    @ApiOperation(value = "创建订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shippingId", value = "用户地址id", required = true, paramType = "query", dataType = "int")
    })
    @PostMapping("create.do")
    public ServerResponse create(@CurrentUser User user, Integer shippingId ) {
        return iOrderService.create(user.getId(),shippingId);
    }

    @ApiOperation(value = "取消订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "订单编号", required = true, paramType = "query", dataType = "long")
    })
    @PostMapping("cancel.do")
    public ServerResponse cancel(@CurrentUser User user, Long orderNo ) {
        return iOrderService.cancel(user.getId(),orderNo);
    }

    @ApiOperation(value = "")
    @GetMapping("get_order_cart_product.do")
    public ServerResponse getOrderCartProduct(@CurrentUser User user) {
        return iOrderService.getOrderCartProduct(user.getId());
    }

    @ApiOperation(value = "订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "订单编号", required = true, paramType = "query", dataType = "long")
    })
    @GetMapping("detail.do")
    public ServerResponse getOrderDetail(@CurrentUser User user,Long orderNo) {
        return iOrderService.getOrderDetail(user.getId(),orderNo);
    }

    @ApiOperation(value = "订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页，默认1", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数量，默认10", paramType = "query", dataType = "int")
    })
    @GetMapping("list.do")
    public ServerResponse list(@CurrentUser User user, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize) {
        return iOrderService.getOrderList(user.getId(),pageNum,pageSize);
    }











    @ApiOperation(value = "订单支付")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "订单编号", required = true, paramType = "query", dataType = "long")
    })
    @PostMapping("pay.do")
    public ServerResponse pay(@CurrentUser User user, Long orderNo, HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("upload");
        return iOrderService.pay(orderNo, user.getId(), path);
    }

    @ApiOperation(value = "支付回调")
    @GetMapping("alipay_callback.do")
    public Object alipayCallback(HttpServletRequest request) {
        Map<String, String> params = Maps.newHashMap();

        Map requestParams = request.getParameterMap();
        for (Object o : requestParams.keySet()) {
            String name = (String) o;
            String[] values = (String[]) requestParams.get(name);
            StringBuilder valueStr = new StringBuilder();
            for (String value : values) {
                valueStr.append(value).append(",");
            }
            params.put(name, valueStr.toString());
        }
        log.info("支付宝回调，sign{},trade_status{},参数:{}", params.get("sign"), params.get("trade_status"), params.toString());

        //非常重要，验证回调的正确性，是不是支付宝发的，并且还要避免重复通知
        params.remove("sign_type");
        try {
            boolean alipayRSAChecked2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(), "utf-8", Configs.getSignType());
            if (!alipayRSAChecked2) {
                return ServerResponse.createByErrorMessage("非法请求，验证不通过，再恶意请求我就找网警了！");
            }

        } catch (AlipayApiException e) {
            log.error("支付宝验证回调异常", e);
        }

        //todo 验证各种数据

        ServerResponse serverResponse = iOrderService.aliCallback(params);
        if (serverResponse.isSuccess()) {
            return Const.AlipayCallback.RESPONSE_SUCCESS;
        }
        return Const.AlipayCallback.RESPONSE_FAILED;
    }

    @ApiOperation(value = "查询支付状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "订单编号", required = true, paramType = "query", dataType = "long")
    })
    @GetMapping("query_order_pay_status.do")
    public ServerResponse<Boolean> queryOrderPayStatus(User user, Long orderNo) {

        ServerResponse serverResponse = iOrderService.queryOrderPayStatus(user.getId(), orderNo);
        if (serverResponse.isSuccess()) {
            return ServerResponse.createBySuccess(true);
        }
        return ServerResponse.createBySuccess(false);
    }
}
