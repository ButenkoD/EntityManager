package com.andersenlab.entity_manager.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product extends AbstractEntity {
    private static final String CANT_CREATE_MESSAGE = "Can't create product, "
            + "expected 2 parameters: name and price\n";
    private int id;
    private String name;
    private int price;
    private List<Purchase> purchases = new ArrayList<>();

    public Product() {}

    public Product(List<String> params) {
        if (params != null && params.size() == 2) {
            this.name = params.get(0);
            this.price = Integer.parseInt(params.get(1));
        } else {
            throw new IllegalArgumentException(CANT_CREATE_MESSAGE);
        }
    }

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @ManyToMany(mappedBy = "products")
    List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    void addPurchase(Purchase purchase) {
        if (purchases.contains(purchase)) {
            return;
        }
        purchases.add(purchase);
        if (!purchase.getProducts().contains(this)) {
            purchase.addProduct(this);
        }
    }

    @Override
    public String toString() {
        return "Id: " + id +", Name: " + name + ", Price: " + price + ";\n";
    }
}
