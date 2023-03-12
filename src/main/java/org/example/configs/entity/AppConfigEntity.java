package org.example.configs.entity;

import lombok.Data;

@Data
public class AppConfigEntity implements ConfigEntity {

    private String id = "app_config";
    private ConfigA configA;
    private ConfigB configB;

    public record ConfigA(int maxSize, double threshold) {
    }

    public record ConfigB(int value, boolean test) {
    }
}
