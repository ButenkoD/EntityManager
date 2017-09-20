package com.andersenlab.entity_manager.entity;

import com.andersenlab.entity_manager.AbstractStorageAction;
import com.andersenlab.entity_manager.Storage;

import java.util.ArrayList;
import java.util.List;

public class PurchaseRepository extends AbstractRepository {
    private static final String CANT_CREATE_MESSAGE = "Can't create purchase, "
            + "expected minimum 2 parameters: customer_id and product_id_1 [product_id_2 ...] \n";
    public String create(List<String> params) throws Exception {

        AbstractStorageAction action = new AbstractStorageAction() {
            @Override
            public Object call() throws Exception {
                Purchase purchase = new Purchase();
                if (params.size() > 1) {
                    int customerId = Integer.parseInt(params.get(0));
                    Customer customer = entityManager.find(Customer.class, customerId);
                    customer.addPurchase(purchase);
                    List<Integer> productIds = new ArrayList<>();
                    for (String productId: params.subList(1, params.size())) {
                        productIds.add(Integer.parseInt(productId));
                    }
                    List<Product> products = entityManager.createQuery(
                            "SELECT p FROM "
                                    + Product.class.getSimpleName()
                                    +" p WHERE p.id IN :ids", Product.class)
                            .setParameter("ids", productIds).getResultList();;
                    purchase.setProducts(products);
                    entityManager.persist(purchase);
                    return "New purchase was created";
                }
                throw new Exception(CANT_CREATE_MESSAGE);
            }
        };
        return (String) Storage.getInstance().performAction(action, true);
    }

    public String remove(List<String> params) throws Exception {
        if (params.size() == 1) {
            return remove(Integer.parseInt(params.get(0)));
        }
        throw new IllegalArgumentException();
    }

    public String remove(int id) throws Exception {
        return Storage.getInstance().remove(Purchase.class, id);
    }

    public String show() throws Exception {
        List purchases = Storage.getInstance().findAll(Purchase.class);
        return objectsToString(purchases);
    }
}
