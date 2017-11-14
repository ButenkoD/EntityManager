package com.andersenlab.entity_manager.entity;

public class RepositoryFactory {
    private static final String CANT_FIND_ENTITY_MESSAGE = "Can't find entity ";
    public static AbstractRepository createRepository(String modelName) throws Exception {
        switch (modelName) {
            case "customer":
                return new CustomerRepository();
            case "product":
                return new ProductRepository();
            case "purchase":
                return new PurchaseRepository();
            default:
                throw new IllegalArgumentException(CANT_FIND_ENTITY_MESSAGE + modelName);
        }
    }

    public static AbstractRepository createRepository2(String modelName) throws Exception {
        String classname = upperCaseFirstCharacter(modelName);
        return getRepositoryObject(classname);
    }

    private static String upperCaseFirstCharacter(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    private static AbstractRepository getRepositoryObject(String modelName) throws Exception {
        String repositoryClassName = AbstractRepository.class.getPackage().getName()+"."+modelName+"Repository";
        try {
            Class<?> myClass = Class.forName(repositoryClassName);
            return (AbstractRepository) myClass.newInstance();
        } catch(Throwable throwable) {
            throw new IllegalArgumentException(CANT_FIND_ENTITY_MESSAGE + modelName);
        }
    }
}
