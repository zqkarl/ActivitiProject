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

@Configuration    // 配置注解，自动在本类上下文加载一些环境变量信�?
@EnableSwagger2   // 使swagger2生效
@EnableWebMvc
@ComponentScan(basePackages = {"com.activiti.controller"})  //�?要扫描的包路�?
public class XaSwaggerConfig extends WebMvcConfigurationSupport {
 
    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("business-api")
                .select()   // 选择那些路径和api会生成document
//.apis(RequestHandlerSelectors.basePackage("net.smgtech.rmm.controller"))
//                .paths(paths())
                .apis(RequestHandlerSelectors.any())  // 对所有api进行监控
                .paths(PathSelectors.any())   // 对所有路径进行监�?
.build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private List<ApiKey> securitySchemes() {
        return newArrayList(
                new ApiKey("clientId", "�ͻ���ID", "header"),
                new ApiKey("clientSecret", "�ͻ�����Կ", "header"),
                new ApiKey("accessToken", "�ͻ��˷��ʱ�ʶ", "header"));
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
                .title("Spring ��ʹ��Swagger2����RESTful API")
                .termsOfServiceUrl("http://blog.csdn.net/yangshijin1988")
                .description("��API�ṩ�ӿڵ���")
                .license("License Version 2.0")
                .licenseUrl("http://blog.csdn.net/yangshijin1988")
                .version("2.0").build();
    }
}