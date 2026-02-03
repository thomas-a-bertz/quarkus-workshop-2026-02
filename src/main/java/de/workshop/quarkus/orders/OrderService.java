package de.workshop.quarkus.orders;


import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;

@ApplicationScoped
public class OrderService {

    private final Map<UUID, OrderDTO> orders = new HashMap<>();

    public Collection<OrderDTO> getOrders() {
        return orders.values();
    }

    public void saveOrder(OrderDTO order) {
        UUID orderId = UUID.randomUUID();
        order.setOrderId(orderId);
        orders.put(orderId, order);
    }

    public Optional<OrderDTO> getOrder(UUID orderId) {
        return Optional.ofNullable(orders.get(orderId));
    }
}
