package com.texoit.movie.docs;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

/**
 * @author Lucas Darú Miguel
 * @linkedin https://www.linkedin.com/in/lucas-daru-miguel-642901233/
 */
public class BaseSwaggerConfig {
    private final String basePackage;
    private final String appName;
    private final String appVersion;
    private final String appDescription;

    public BaseSwaggerConfig(String basePackage, String appName, String appVersion, String appDescription) {
        this.basePackage = basePackage;
        this.appName = appName;
        this.appVersion = appVersion;
        this.appDescription = appDescription;
    }

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .build().globalResponseMessage(RequestMethod.POST, globalResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, globalResponseMessages())
                //                .globalOperationParameters(globalOperationParameters())
                .apiInfo(apiInfo()).enableUrlTemplating(false);
    }

    private ArrayList<Parameter> globalOperationParameters() {
        return Lists.newArrayList(new ParameterBuilder().name("Authorization").description("Authorization Token")
                .modelRef(new ModelRef("string")).parameterType("header").required(false).build());
    }

    private ArrayList<ResponseMessage> globalResponseMessages() {
        return Lists.newArrayList(new ResponseMessageBuilder()
                .code(409)
                .message("Failed to make reservation!")
                .responseModel(new ModelRef("Error"))
                .build(),new ResponseMessageBuilder()
                .code(400)
                .message("Form is invalid!")
                .responseModel(new ModelRef("Error"))
                .build()
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(appName)
                .description(appDescription)
                .version(appVersion)
                .build();
    }
}