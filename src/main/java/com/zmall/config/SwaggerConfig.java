package com.zmall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author duanxin
 * @className: SwaggerConfig
 * @date 2019-12-30 09:26
 **/

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .tags(new Tag("user","普通用户api"),tags())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zmall.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("zmall电商 APIS")
                .description("基础服务(用户基本信息不用填写：即id,username,password,phone,question,answer,role,createTime,updateTime字段)")
//                .termsOfServiceUrl("http://192.168.1.198:10070/platformgroup/ms-admin")
                .version("1.0")
                .build();
    }

    private Tag[] tags(){
        ArrayList<Tag> tagArrayList = new ArrayList<>();
        tagArrayList.add(new Tag("cart","用户购物车api"));
        tagArrayList.add(new Tag("order","用户订单api"));
        tagArrayList.add(new Tag("product","商品api"));
        tagArrayList.add(new Tag("shipping","地址相关api"));

        Tag[] tags = new Tag[tagArrayList.size()];
        return tagArrayList.toArray(tags);
    }

}
