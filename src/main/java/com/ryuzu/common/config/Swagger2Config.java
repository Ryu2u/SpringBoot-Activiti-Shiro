package com.ryuzu.common.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger2默认地址
 * http://localhost:8080/swagger-ui.html#/
 * swagger2-bootstrap地址
 * http://localhost:8080/doc.html
 * @author Ryuzu
 * @date 2022/7/20 15:18
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {



    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("swagger2文档")
                .description("后台接口测试")
                .contact(new Contact("ryuzu", "http://localhost:8080/swagger/doc.html", "ryu2u@qq.com"))
                .version("1.0")
                .build();
    }

    /**
     * 设置请求头
     *
     * @return
     */
    public List<SecurityScheme> securitySchemes(){
        List<SecurityScheme> list = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "Header");
        list.add(apiKey);
        return list;

    }

    /**
     *
     * @return
     */
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build().securitySchemes(securitySchemes());
    }




}
