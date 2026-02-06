package de.workshop.quarkus.orders.util;

import de.workshop.quarkus.orders.domain.OrderEntity;

import java.util.UUID;

public enum OrderEntityTestFactory {

    TEST_ENTITY1(UUID.fromString("42cc658c-25a9-400f-8bcb-c525586a2e28"), "lastname", "someItem", 42),
    TEST_ENTITY2(UUID.fromString("3b4d87bc-58b3-41c0-80a3-d45a1de5480f"), "Nachname", "einProdukt", 18);

    private final UUID orderId;
    private final String lastname;
    private final String itemDescription;
    private final int amount;

    OrderEntityTestFactory(UUID orderId, String lastname, String itemDescription, int amount) {
        this.orderId = orderId;
        this.lastname = lastname;
        this.itemDescription = itemDescription;
        this.amount = amount;
    }

    public OrderEntity create() {
        return new OrderEntity(orderId, lastname, itemDescription, amount);
    }
}
