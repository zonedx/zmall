package com.zmall.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ClassName: OrderProductVo
 * @Date 2019-09-23 00:11
 * @Author duanxin
 **/
public class OrderProductVo {

    private List<OrderItemVo> orderItemVoList;
    private BigDecimal productTotalPrice;
    private String imageHost;

    public List<OrderItemVo> getOrderItemVoList() {
        return orderItemVoList;
    }

    public void setOrderItemVoList(List<OrderItemVo> orderItemVoList) {
        this.orderItemVoList = orderItemVoList;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
