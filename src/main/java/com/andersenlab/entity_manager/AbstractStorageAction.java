package com.andersenlab.entity_manager;

import javax.persistence.EntityManager;
import java.util.concurrent.Callable;

public abstract class AbstractStorageAction implements Callable<Object> {
    protected EntityManager entityManager;

    void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public abstract Object call() throws Exception;
}
