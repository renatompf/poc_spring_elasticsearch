package io.github.renatompf.springelasticpoc.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories(
    basePackages = "io.github.renatompf.springelasticpoc.repository.elastic"
)
@Configuration
public class ElasticsearchConfig {
}
