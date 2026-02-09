package de.workshop.quarkus.orders.util;

import java.util.UUID;

public enum OrderDTOTestFactory {

    TEST_DTO1(UUID.fromString("42cc658c-25a9-400f-8bcb-c525586a2e28"), "lastname", "firstname", "someItem", 42),
    TEST_DTO2(UUID.fromString("3b4d87bc-58b3-41c0-80a3-d45a1de5480f"), "Nachname", "Vorname", "einProdukt", 18);

    private final UUID orderId;
    private final String lastname;
    private final String firstname;
    private final String itemDescription;
    private final int amount;

    OrderDTOTestFactory(UUID orderId, String lastname, String firstname, String itemDescription, int amount) {
        this.orderId = orderId;
        this.lastname = lastname;
        this.firstname = firstname;
        this.itemDescription = itemDescription;
        this.amount = amount;
    }

    public OrderDTO create() {
        return new OrderDTO(orderId, lastname, firstname, itemDescription, amount);
    }
}
