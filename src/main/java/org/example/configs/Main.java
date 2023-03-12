package org.example.configs;

import org.example.configs.service.ConfigProvider;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static org.example.configs.entity.AppConfigEntity.ConfigA;
import static org.example.configs.entity.AppConfigEntity.ConfigB;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

    @Bean
    public ApplicationRunner testRunner(ConfigProvider<ConfigA> appConfigAProvider,
                                        ConfigProvider<ConfigB> appConfigBProvider,
                                        ConfigProvider<String> textTestConfigBProvider) {
        return (arguments) -> {
            ConfigA configA = appConfigAProvider.getConfig();
            ConfigB configB = appConfigBProvider.getConfig();
            String text = textTestConfigBProvider.getConfig();
            System.out.println();
        };
    }
}
