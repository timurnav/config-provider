package org.example.configs.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class ConfigProviderBeanPostProcessor implements BeanPostProcessor {

    @Override
    @SuppressWarnings("NullableProblems")
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ConfigProviderImpl) {
            ((ConfigProviderImpl<?, ?>) bean).ensureCreated();
        }
        return bean;
    }
}
