package com.andersenlab.entity_manager;

import javax.persistence.EntityManager;
import java.util.concurrent.Callable;

public abstract class AbstractStorageAction <T> implements Callable<T> {
    protected EntityManager entityManager;

    void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public abstract T call() throws Exception;
}
