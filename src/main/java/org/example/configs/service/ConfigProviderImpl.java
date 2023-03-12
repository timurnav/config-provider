package org.example.configs.service;

import org.example.configs.entity.ConfigEntity;

import java.util.Optional;
import java.util.function.Function;

import static org.example.configs.utils.Lambdas.provideGetter;

public class ConfigProviderImpl<T, E extends ConfigEntity>
        implements ConfigProvider<T> {

    private final ConfigService<E> configService;
    private final String fieldName;
    private final Function<E, T> getter;
    private final T defaultValue;

    @SuppressWarnings("unchecked")
    public ConfigProviderImpl(ConfigService<E> configService,
                                 String fieldName, T defaultValue) {
        this.configService = configService;
        this.fieldName = fieldName;
        this.defaultValue = defaultValue;
        this.getter = provideGetter(fieldName,
                (Class<T>) defaultValue.getClass(),
                configService.getType());
    }

    public T getConfig() {
        return Optional.ofNullable(configService.getConfig())
                .map(getter)
                .orElse(defaultValue);
    }

    void ensureCreated() {
        configService.ensureCreated(fieldName, defaultValue);
    }
}
