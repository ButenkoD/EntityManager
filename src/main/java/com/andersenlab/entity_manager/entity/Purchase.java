package com.andersenlab.entity_manager.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchase")
public class Purchase {
    private static final String CANT_CREATE_MESSAGE = "Can't create purchase, "
            + "expected 2 parameters: customer and list of products\n";
    private int id;
    private Timestamp createdAt;
    private Customer customer;
    private List<Product> products = new ArrayList<>();

    public Purchase() {
    }

    public Purchase(Customer customer, List<Product> products) {
        if (customer != null && products != null && !products.isEmpty()) {
            this.customer = customer;
            this.products = products;
        }
        throw new IllegalArgumentException(CANT_CREATE_MESSAGE);
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

    @Column(name = "date")
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @ManyToOne
    @JoinColumn(name="customer_id")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
        name = "purchase_product",
        joinColumns = {
            @JoinColumn(name = "purchase_id", referencedColumnName = "id")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "product_id", referencedColumnName = "id")
        }
    )
    List<Product> getProducts() {
        return products;
    }

    void setProducts(List<Product> products) {
        this.products = products;
    }

    void addProduct(Product product) {
        if (products.contains(product)) {
            return;
        }
        products.add(product);
        if (!product.getPurchases().contains(this)) {
            product.addPurchase(this);
        }
    }

    @Override
    public String toString() {
        return "Id: " + id +" Timestamp: " + createdAt + "\n";
    }
}
