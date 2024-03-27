package com.texoit.movie.config;

import com.texoit.movie.docs.BaseSwaggerConfig;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Lucas Darú Miguel
 * @linkedin https://www.linkedin.com/in/lucas-daru-miguel-642901233/
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    private static final String BASE_PACKAGE = "com.texoit.movie.controller";
    private static final String APP_NAME = "Swagger API TexoIT";
    private static final String APP_VERSION = "0.0.1-SNAPSHOT";
    private static final String APP_DESCRIPTION = "Microservice Project for TexoIT";

    public SwaggerConfig() {
        super(BASE_PACKAGE, APP_NAME, APP_VERSION, APP_DESCRIPTION);
    }

}
