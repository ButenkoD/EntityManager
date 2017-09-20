package com.andersenlab.entity_manager.entity;

import com.andersenlab.entity_manager.Storage;
import javassist.NotFoundException;

import java.util.List;

public class CustomerRepository extends AbstractRepository {
    private static final String CANT_FOUND_MESSAGE = "Can't found customer with id ";
    public String create(List<String> params) throws Exception {
        Customer customer = new Customer(params);
        return Storage.getInstance().save(customer);
    }

    public String remove(List<String> params) throws Exception {
        if (params.size() == 1) {
            return remove(Integer.parseInt(params.get(0)));
        }
        throw new IllegalArgumentException();
    }

    public Customer findById(int id) throws Exception {
        Customer customer = (Customer) Storage.getInstance().find(Customer.class, id);
        if (customer == null) {
            throw new NotFoundException(CANT_FOUND_MESSAGE + Integer.toString(id));
        }
        return customer;
    }

    public String remove(int id) throws Exception {
        return Storage.getInstance().remove(Customer.class, id);
    }

    public String show() throws Exception {
        List customers = Storage.getInstance().findAll(Customer.class);
        return objectsToString(customers);
    }
}