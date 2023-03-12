package org.example.configs.configuration;

import org.example.configs.entity.AppConfigEntity;
import org.example.configs.entity.AppConfigEntity.ConfigA;
import org.example.configs.entity.AppConfigEntity.ConfigB;
import org.example.configs.entity.TestConfigEntity;
import org.example.configs.service.ConfigProvider;
import org.example.configs.service.ConfigProviderImpl;
import org.example.configs.service.ConfigService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;

@Configuration
public class ProviderConfiguration {

    @Bean
    public ConfigProvider<ConfigA> appConfigAProvider(ConfigService<AppConfigEntity> appConfigService) {
        ConfigA defaultValue = new ConfigA(20, .7);
        return new ConfigProviderImpl<>(appConfigService, "configA", defaultValue);
    }

    @Bean
    public ConfigProvider<ConfigB> appConfigBProvider(ConfigService<AppConfigEntity> appConfigService) {
        ConfigB defaultValue = new ConfigB(100, true);
        return new ConfigProviderImpl<>(appConfigService, "configB", defaultValue);
    }

    @Bean
    public ConfigProvider<String> textTestConfigBProvider(ConfigService<TestConfigEntity> testConfigService) {
        return new ConfigProviderImpl<>(testConfigService, "text", "Some very valuable text");
    }

    @Bean
    public ConfigService<AppConfigEntity> appConfigService(MongoOperations mongoOperations) {
        return new ConfigService<>(mongoOperations, new AppConfigEntity());
    }

    @Bean
    public ConfigService<TestConfigEntity> testConfigService(MongoOperations mongoOperations) {
        return new ConfigService<>(mongoOperations, new TestConfigEntity());
    }
}
