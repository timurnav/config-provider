package org.example.configs.entity;

import lombok.Data;

@Data
public class TestConfigEntity implements ConfigEntity {

    private String id = "test_config";
    private String text;
}
