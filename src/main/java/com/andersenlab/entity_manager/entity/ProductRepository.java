package com.andersenlab.entity_manager.entity;

import com.andersenlab.entity_manager.Storage;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository extends AbstractRepository {
    public String create(List<String> params) throws Exception {
        Product product = new Product(params);
        return Storage.getInstance().save(product);
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
        List products = Storage.getInstance().findAll(Product.class);
        return objectsToString(products);
    }

    public List<Product> findAllByIds(List<Integer> ids) throws Exception {
        List<Product> products = new ArrayList<>();
        List objects = Storage.getInstance().findAllByIds(Product.class, ids);
        for(Object o : objects) {
            products.add((Product) o);
        }
        return products;
    }
}
