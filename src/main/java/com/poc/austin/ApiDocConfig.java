package com.poc.austin;

import static springfox.documentation.builders.PathSelectors.any;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * This calls to build Swagger AIP doc
 * @author Somnath
 *
 */
@Configuration
@EnableSwagger2

public class ApiDocConfig {
  
    private static final String SWAGGER_GROUP = "demo-resful-application";
    private static final String SWAGGER_BASE_PACKAGE = "com.cromecast.controller";

    
    @Bean
    public Docket newsApi() {       
        
        return new Docket(DocumentationType.SWAGGER_2).groupName(SWAGGER_GROUP).apiInfo(apiInfo())
                .enable(true)
                .select().paths(any()).apis(RequestHandlerSelectors.basePackage(SWAGGER_BASE_PACKAGE))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Created for demo Sepcification").version("V1")                
                .contact("Somnath Banerje (banerjso30@gmail.com) ").version("1.0").build();
    }
}
