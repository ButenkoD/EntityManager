package com.andersenlab.entity_manager.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer extends AbstractEntity {
    private static final String CANT_CREATE_MESSAGE = "Can't create customer, "
        + "expected 2 parameters: name and surname\n";
    private int id;
    private String name;
    private String surname;
    private List<Purchase> purchases = new ArrayList<>();

    public Customer() {
    }

    public Customer(List<String> params) {
        if (params != null && params.size() == 2) {
            this.name = params.get(0);
            this.surname = params.get(1);
        } else {
            throw new IllegalArgumentException(CANT_CREATE_MESSAGE);
        }
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

    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @OneToMany(mappedBy="customer", fetch = FetchType.EAGER)
    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public void addPurchase(Purchase purchase) {
        purchases.add(purchase);
        if (purchase.getCustomer() != this) {
            purchase.setCustomer(this);
        }
    }

    @Override
    public String toString() {
        return "Id: " + id +", Name: " + name + ", Surname: " + surname + ";\n";
    }
}
