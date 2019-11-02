package com.zone.test.service;

import com.github.pagehelper.PageInfo;
import com.zmall.common.ServerResponse;
import com.zmall.service.IProductService;
import com.zmall.service.impl.ProductServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @ClassName: ProductServiceTest
 * @Date 2019-10-17 22:00
 * @Author duanxin
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class ProductServiceTest {

    @Autowired
    private IProductService iProductService;

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void getProductListTest(){

        ServerResponse<PageInfo> serverResponse = productService.getProductList(1,10);
//        serverResponse.getData();
        System.out.println(serverResponse.getData());
    }
}
