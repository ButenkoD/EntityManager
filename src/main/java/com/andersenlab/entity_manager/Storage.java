package com.andersenlab.entity_manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Storage {
    private final static String PERSISTENCE_UNIT_NAME = "jpa.hibernate";
    private EntityManagerFactory entityManagerFactory;
    public static class Holder {
        static Storage instance = new Storage();
    }

    public static Storage getInstance() {
        return Holder.instance;
    }

    private Storage() {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(
                    PERSISTENCE_UNIT_NAME
            );
        } catch (Throwable throwable) {
            System.out.println(throwable.getMessage());
        }
    }

    public <T> String save(T object, Class<T> clazz) throws Exception {
        AbstractStorageAction<String> action = new AbstractStorageAction<String>() {
            @Override
            public String call() throws Exception {
                entityManager.persist(object);
                List<T> objects = selectAll(clazz, entityManager);
                return objectsToString(objects);
            }
        };
        return performAction(action, true);
    }

    public <T> List<T> findAll(Class<T> clazz) throws Exception {
        AbstractStorageAction<List<T>> action = new AbstractStorageAction<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return selectAll(clazz, entityManager);
            }
        };
        return performAction(action, false);
    }

    public <T> T find(Class<T> clazz, int id) throws Exception {
        AbstractStorageAction<T> action = new AbstractStorageAction<T>() {
            @Override
            public T call() throws Exception {
                return entityManager.find(clazz, id);
            }
        };
        return performAction(action, false);
    }

    public <T> List<T> findAllByIds(Class<T> clazz, List<Integer> ids) throws Exception {
        AbstractStorageAction<List<T>> action = new AbstractStorageAction<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return entityManager
                    .createQuery("SELECT p FROM " + clazz.getSimpleName() +" p WHERE p.id IN :ids", clazz)
                    .setParameter("ids", ids)
                    .getResultList();
            }
        };
        return performAction(action, false);
    }

    public <T> String remove(Class<T> clazz, int id) throws Exception {
        AbstractStorageAction<String> action = new AbstractStorageAction<String>() {
            @Override
            public String call() throws Exception {
                Object object = entityManager.find(clazz, id);
                entityManager.remove(object);
                return "This object was removed " + object.toString();
            }
        };
        return performAction(action, true);
    }

    private <T> List<T> selectAll(Class<T> clazz, EntityManager entityManager) {
        return entityManager
            .createQuery("select c from "+clazz.getSimpleName()+" c", clazz)
            .getResultList();
    }

    private <T> String objectsToString(List<T> objects) {
        if (objects.isEmpty()) {
            return "Empty table";
        }
        StringBuilder stringBuilder = new StringBuilder();
        objects.forEach(stringBuilder::append);
        return stringBuilder.toString();
    }

    public <T> T performAction(AbstractStorageAction<T> action, boolean viaTransaction) throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        if (viaTransaction) {
            transaction.begin();
        }
        try {
            action.setEntityManager(entityManager);
            T result = action.call();
            if (viaTransaction) {
                transaction.commit();
            }
            return result;
        } catch (Throwable throwable) {
            if (viaTransaction) {
                transaction.rollback();
            }
            throw throwable;
        } finally {
            entityManager.close();
        }
    }
}
