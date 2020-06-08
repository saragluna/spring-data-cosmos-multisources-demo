package com.microsoft.azure;

import com.azure.data.cosmos.CosmosKeyCredential;
import com.microsoft.azure.spring.data.cosmosdb.CosmosDbFactory;
import com.microsoft.azure.spring.data.cosmosdb.config.AbstractCosmosConfiguration;
import com.microsoft.azure.spring.data.cosmosdb.config.CosmosDBConfig;
import com.microsoft.azure.spring.data.cosmosdb.core.ReactiveCosmosTemplate;
import com.microsoft.azure.spring.data.cosmosdb.core.convert.MappingCosmosConverter;
import com.microsoft.azure.spring.data.cosmosdb.repository.config.EnableReactiveCosmosRepositories;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@Slf4j
public class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "azure.cosmosdb.primary")
    public CosmosDbProperties primaryDataSourceConfiguration() {
        return new CosmosDbProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "azure.cosmosdb.secondary")
    public CosmosDbProperties secondaryDataSourceConfiguration(){
        return new CosmosDbProperties();
    }

    @EnableReactiveCosmosRepositories(basePackages = "com.microsoft.azure.datasource1", reactiveCosmosTemplateRef = "reactiveCosmosTemplate2")
    public static class DataSource1Configuration {

        @Autowired
        @Qualifier("primaryDataSourceConfiguration")
        private CosmosDbProperties properties;

        @Bean
        public ReactiveCosmosTemplate reactiveCosmosTemplate2(MappingCosmosConverter mappingCosmosConverter) {
            CosmosDBConfig cosmosDBConfig = CosmosDBConfig.builder(properties.getUri(),
                    new CosmosKeyCredential(properties.getKey()),
                    properties.getDatabase()).build();
            cosmosDBConfig.setPopulateQueryMetrics(properties.isPopulateQueryMetrics());

            return new ReactiveCosmosTemplate(new CosmosDbFactory(cosmosDBConfig), mappingCosmosConverter, cosmosDBConfig.getDatabase());
        }
    }

    @EnableReactiveCosmosRepositories(basePackages = "com.microsoft.azure.datasource2")
    public static class DataSource2Configuration extends AbstractCosmosConfiguration {

        @Autowired
        @Qualifier("secondaryDataSourceConfiguration")
        private CosmosDbProperties properties;

        @Bean
        public CosmosDBConfig cosmosDbConfig() {
            CosmosDBConfig cosmosDBConfig = CosmosDBConfig.builder(properties.getUri(),
                    new CosmosKeyCredential(properties.getKey()),
                    properties.getDatabase()).build();
            cosmosDBConfig.setPopulateQueryMetrics(properties.isPopulateQueryMetrics());
            return cosmosDBConfig;
        }

    }

}
