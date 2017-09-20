package com.andersenlab.entity_manager.entity;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRepository {
    private List<String> accessibleMethods;

    public boolean hasAccessibleMethod(String methodName) {
        return getAccessibleMethods().contains(methodName);
    }

    public List<String> getAccessibleMethods() {
        if (accessibleMethods == null) {
            accessibleMethods = new ArrayList<>();
            for (Method method : this.getClass().getDeclaredMethods()) {
                if (isPublicNonStaticMethod(method))
                    accessibleMethods.add(method.getName());
            }
        }
        return accessibleMethods;
    }

    private static boolean isPublicNonStaticMethod(Method method) {
        int mod = method.getModifiers();
        return !Modifier.isStatic(mod) && Modifier.isPublic(mod);
    }

    protected String objectsToString(List<?> objects) {
        if (objects.isEmpty()) {
            return "Empty table";
        }
        StringBuilder stringBuilder = new StringBuilder();
        objects.forEach(stringBuilder::append);
        return stringBuilder.toString();
    }
}
