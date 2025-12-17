package io.github.renatompf.springelasticpoc.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(
    basePackages = "io.github.renatompf.springelasticpoc.repository.jpa"
)
@Configuration
public class JpaConfig {
}
