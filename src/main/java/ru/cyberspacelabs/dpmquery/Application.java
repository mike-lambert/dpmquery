package ru.cyberspacelabs.dpmquery;

import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Nullable;

@SpringBootApplication
@ComponentScan
@EnableSwagger2
public class Application extends SpringBootServletInitializer {
    /** The title for the spring boot service to be displayed on swagger UI. */
    @Value("${swagger.title}")
    private String title;

    /** The description of the spring boot service. */
    @Value("${swagger.description}")
    private String description;

    /** The version of the service. */
    @Value("${swagger.version}")
    private String version;

    /** The terms of service url for the service if applicable. */
    @Value("${swagger.termsOfServiceUrl}")
    private String termsOfServiceUrl;

    /** The contact name for the service. */
    @Value("${swagger.contact.name}")
    private String contactName;

    /** The contact url for the service. */
    @Value("${swagger.contact.url}")
    private String contactURL;

    /** The contact email for the service. */
    @Value("${swagger.contact.email}")
    private String contactEmail;

    /** The license for the service if applicable. */
    @Value("${swagger.license}")
    private String license;

    /** The license url for the service if applicable. */
    @Value("${swagger.licenseUrl}")
    private String licenseURL;
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(new Predicate<String>() {
                    @Override
                    public boolean apply(@Nullable String input) {
                        return input != null && input.startsWith("/api/v1/");
                    }
                })
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(title, description, version, termsOfServiceUrl,
                new Contact(contactName, contactURL, contactEmail), license, licenseURL
        );
    }
}
