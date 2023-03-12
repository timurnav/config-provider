package org.example.configs.service;

import lombok.extern.slf4j.Slf4j;
import org.example.configs.entity.ConfigEntity;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static com.google.common.base.Suppliers.memoizeWithExpiration;

@Slf4j
public class ConfigService<E extends ConfigEntity> {

    public static final String COLLECTION_NAME = "app_config";

    private final MongoOperations mongoOperations;
    private final E initial;

    private final Supplier<E> configEntitySupplier =
            memoizeWithExpiration(this::fetchConfig, 60, TimeUnit.SECONDS);

    public ConfigService(MongoOperations mongoOperations, E initial) {
        this.mongoOperations = mongoOperations;
        this.initial = initial;
    }

    public E getConfig() {
        return configEntitySupplier.get();
    }

    public void ensureCreated(String fieldName, Object value) {
        mongoOperations.upsert(
                Query.query(Criteria.where("_id").is(initial.getId())),
                Update.update("_id", initial.getId()),
                getType(), COLLECTION_NAME);

        Query query = Query.query(Criteria.where("_id").is(initial.getId()).and(fieldName).isNull());
        Update update = new Update();
        update.set(fieldName, value);
        mongoOperations.findAndModify(query, update, getType(), COLLECTION_NAME);
    }

    private E fetchConfig() {
        Query query = Query.query(Criteria.where("_id").is(initial.getId()));
        return mongoOperations.findOne(query, getType(), COLLECTION_NAME);
    }

    @SuppressWarnings("unchecked")
    public Class<E> getType() {
        return (Class<E>) initial.getClass();
    }
}
