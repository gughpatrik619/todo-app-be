package com.example.todoapp.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfiguration {

    // http://localhost:8081/swagger-ui/

    @Bean
    fun swaggerAuth(): Docket =
        Docket(DocumentationType.SWAGGER_2)
            .groupName("AUTH")
            .select()
            .paths(PathSelectors.ant("/auth/**"))
            .apis(RequestHandlerSelectors.any())
            .build()
            .apiInfo(getAuthInfo())

    @Bean
    fun swaggerApi(): Docket =
        Docket(DocumentationType.SWAGGER_2)
            .groupName("API")
            .select()
            .paths(PathSelectors.ant("/api/**"))
            .apis(RequestHandlerSelectors.any())
            .build()
            .apiInfo(getApiInfo())

    private fun getAuthInfo() =
        ApiInfoBuilder()
            .title("TODO-AUTH")
            .version("1.0")
            .description("REST API documentation for TODO app authorization")
            .contact(getCorporateContact())
            .build()

    private fun getApiInfo() =
        ApiInfoBuilder()
            .title("TODO-API")
            .version("1.0")
            .description("REST API documentation for TODO application")
            .contact(getCorporateContact())
            .build()

    // todo: change url
    private fun getCorporateContact() =
        Contact(
            "Darius Data Solutions",
            "https://at-frontend.firebaseapp.com",
            "dariusdatasolutions@gmail.com"
        )

}