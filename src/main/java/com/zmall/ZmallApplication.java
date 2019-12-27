package com.zmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author duanxin
 * @className: ZmallApplication
 * @date 2019-12-27 13:44
 **/
@SpringBootApplication(scanBasePackages = {"com.zmall"})
@MapperScan("com.zmall.dao")
public class ZmallApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZmallApplication.class,args);
    }
}
