package de.workshop.quarkus.orders.boundary;

import de.workshop.quarkus.orders.shared.validation.Alphabetic;
import de.workshop.quarkus.orders.shared.validation.Even;
import jakarta.validation.constraints.*;

import java.util.Objects;
import java.util.UUID;

public class OrderDTO {
    private UUID orderId;

    @NotBlank
    @Size(min = 2, max = 10)
    private String customerLastname;

    private String customerFirstname;

    @Alphabetic
    private String itemDescription;

    @NotNull
    @Even
    @Positive
    private int amount;

    public OrderDTO() {
    }

    public OrderDTO(UUID orderId) {
        this.orderId = orderId;
    }

    public OrderDTO(UUID orderId, String customerLastname, String customerFirstname, String itemDescription, int amount) {
        this.orderId = orderId;
        this.customerLastname = customerLastname;
        this.customerFirstname = customerFirstname;
        this.itemDescription = itemDescription;
        this.amount = amount;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return getAmount() == orderDTO.getAmount() && Objects.equals(getOrderId(), orderDTO.getOrderId()) && Objects.equals(getCustomerLastname(), orderDTO.getCustomerLastname()) && Objects.equals(getCustomerFirstname(), orderDTO.getCustomerFirstname()) && Objects.equals(getItemDescription(), orderDTO.getItemDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderId(), getCustomerLastname(), getCustomerFirstname(), getItemDescription(), getAmount());
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId=" + orderId +
                ", customerLastname='" + customerLastname + '\'' +
                ", customerFirstname='" + customerFirstname + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", amount=" + amount +
                '}';
    }
}
