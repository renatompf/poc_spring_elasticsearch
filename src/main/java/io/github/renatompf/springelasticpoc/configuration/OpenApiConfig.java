package io.github.renatompf.springelasticpoc.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Spring ElasticSearch POC")
                .version("1.0")
                .description(
                        """
                        This is a Proof of Concept (POC) project demonstrating
                        the integration of Spring Boot with Elasticsearch
                        for efficient product management and search capabilities.
                        """
                        )
                .contact(new Contact()
                    .name("Renato Freire")
                    .url("https://github.com/renatompf")
                ));
    }
}