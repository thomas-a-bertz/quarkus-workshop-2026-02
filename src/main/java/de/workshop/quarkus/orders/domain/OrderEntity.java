package de.workshop.quarkus.orders.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "ORDERS")
public class OrderEntity {

    @Id
    @GeneratedValue
    private Long id;
    private UUID orderId;
    private String customerLastname;
    private String customerFirstname;
    private String itemDescription;
    private int amount;

    public OrderEntity() {
    }

    public OrderEntity(UUID orderId) {
        this.orderId = orderId;
    }

    public OrderEntity(UUID orderId, String customerLastname, String customerFirstname, String itemDescription, int amount) {
        this.orderId = orderId;
        this.customerLastname = customerLastname;
        this.customerFirstname = customerFirstname;
        this.itemDescription = itemDescription;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public String getCustomerLastname() {
        return customerLastname;
    }

    public void setCustomerLastname(String customerLastname) {
        this.customerLastname = customerLastname;
    }

    public String getCustomerFirstname() {
        return customerFirstname;
    }

    public void setCustomerFirstname(String customerFirstname) {
        this.customerFirstname = customerFirstname;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
