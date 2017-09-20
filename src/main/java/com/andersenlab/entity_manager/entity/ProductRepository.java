package com.andersenlab.entity_manager.entity;

import com.andersenlab.entity_manager.Storage;
import com.google.common.collect.Interner;
import com.google.common.collect.Iterables;
import javassist.NotFoundException;

import java.util.List;
import java.util.function.Function;

public class ProductRepository extends AbstractRepository {
    public String create(List<String> params) throws Exception {
        Product product = new Product(params);
        return Storage.getInstance().save(product);
    };

    public String remove(List<String> params) throws Exception {
        if (params.size() == 1) {
            return remove(Integer.parseInt(params.get(0)));
        }
        throw new IllegalArgumentException();
    }

    public String remove(int id) throws Exception {
        return Storage.getInstance().remove(Product.class, id);
    }

    public String show() throws Exception {
        List products = Storage.getInstance().findAll(Product.class);
        return objectsToString(products);
    }

    public List<Product> findAllByIds(List<Integer> ids) throws Exception {
        return (List<Product>) Storage.getInstance().findAllByIds(Product.class, ids);
    }
}
