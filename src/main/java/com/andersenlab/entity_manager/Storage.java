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

    public String save(Object object) throws Exception {
        AbstractStorageAction action = new AbstractStorageAction() {
            @Override
            public String call() throws Exception {
                entityManager.persist(object);
                String classname = object.getClass().getSimpleName();
                List objects = selectAll(classname, entityManager);
                return objectsToString(objects);
            }
        };
        return (String) performAction(action, true);
    }

    public List findAll(Class classObject) throws Exception {
        AbstractStorageAction action = new AbstractStorageAction() {
            @Override
            public List call() throws Exception {
                return selectAll(classObject.getSimpleName(), entityManager);
            }
        };
        return (List) performAction(action, false);
    }

    public Object find(Class<?> classObject, int id) throws Exception {
        AbstractStorageAction action = new AbstractStorageAction() {
            @Override
            public Object call() throws Exception {
                return entityManager.find(classObject, id);
            }
        };
        return performAction(action, false);
    }

    public List findAllByIds(Class<?> classObject, List<Integer> ids) throws Exception {
        AbstractStorageAction action = new AbstractStorageAction() {
            @Override
            public Object call() throws Exception {
                return entityManager.createQuery(
                            "SELECT p FROM "
                            + classObject.getSimpleName()
                            +" p WHERE p.id IN :ids", classObject)
                        .setParameter("ids", ids).getResultList();
            }
        };
        return (List) performAction(action, false);
    }

    public String remove(Class<?> classObject, int id) throws Exception {
        AbstractStorageAction action = new AbstractStorageAction() {
            @Override
            public String call() throws Exception {
                Object object = entityManager.find(classObject, id);
                entityManager.remove(object);
                return "This object was removed " + object.toString();
            }
        };
        return (String) performAction(action, true);
    }

    private List selectAll(String classname, EntityManager entityManager) {
        return entityManager.createQuery("select c from "+classname+" c", Object.class).getResultList();
    }

    private String objectsToString(List<?> objects) {
        if (objects.isEmpty()) {
            return "Empty table";
        }
        StringBuilder stringBuilder = new StringBuilder();
        objects.forEach(stringBuilder::append);
        return stringBuilder.toString();
    }

    public Object performAction(AbstractStorageAction action, boolean viaTransaction) throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        if (viaTransaction) {
            transaction.begin();
        }
        try {
            action.setEntityManager(entityManager);
            Object result = action.call();
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
