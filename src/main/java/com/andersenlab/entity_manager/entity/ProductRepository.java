package com.andersenlab.entity_manager.entity;

import com.andersenlab.entity_manager.Storage;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository extends AbstractRepository {
    public String create(List<String> params) throws Exception {
        Product product = new Product(params);
        return Storage.getInstance().save(product, Product.class);
    }

    public String remove(List<String> params) throws Exception {
        if (params.size() == 1) {
            return remove(Integer.parseInt(params.get(0)));
        }
        throw new IllegalArgumentException();
    }

    private String remove(int id) throws Exception {
        return Storage.getInstance().remove(Product.class, id);
    }

    public String show() throws Exception {
        List<Product> products = Storage.getInstance().findAll(Product.class);
        return objectsToString(products);
    }

    public List<Product> findAllByIds(List<Integer> ids) throws Exception {
        return Storage.getInstance().findAllByIds(Product.class, ids);
    }
}
