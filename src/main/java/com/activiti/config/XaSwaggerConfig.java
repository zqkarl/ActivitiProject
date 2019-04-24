package com.activiti.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Configuration    // 閰嶇疆娉ㄨВ锛岃嚜鍔ㄥ湪鏈被涓婁笅鏂囧姞杞戒竴浜涚幆澧冨彉閲忎俊鎭?
@EnableSwagger2   // 浣縮wagger2鐢熸晥
@EnableWebMvc
@ComponentScan(basePackages = {"com.activiti.controller"})  //闇?瑕佹壂鎻忕殑鍖呰矾寰?
public class XaSwaggerConfig extends WebMvcConfigurationSupport {
 
    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("business-api")
                .select()   // 閫夋嫨閭ｄ簺璺緞鍜宎pi浼氱敓鎴恉ocument
//.apis(RequestHandlerSelectors.basePackage("net.smgtech.rmm.controller"))
//                .paths(paths())
                .apis(RequestHandlerSelectors.any())  // 瀵规墍鏈塧pi杩涜鐩戞帶
                .paths(PathSelectors.any())   // 瀵规墍鏈夎矾寰勮繘琛岀洃鎺?
.build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private List<ApiKey> securitySchemes() {
        return newArrayList(
                new ApiKey("clientId", "客户端ID", "header"),
                new ApiKey("clientSecret", "客户端秘钥", "header"),
                new ApiKey("accessToken", "客户端访问标识", "header"));
    }

    private List<SecurityContext> securityContexts() {
        return newArrayList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("/*.*"))
                        .build()
        );
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(
                new SecurityReference("clientId", authorizationScopes),
                new SecurityReference("clientSecret", authorizationScopes),
                new SecurityReference("accessToken", authorizationScopes));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring 中使用Swagger2构建RESTful API")
                .termsOfServiceUrl("http://blog.csdn.net/yangshijin1988")
                .description("此API提供接口调用")
                .license("License Version 2.0")
                .licenseUrl("http://blog.csdn.net/yangshijin1988")
                .version("2.0").build();
    }
}