package de.workshop.quarkus.orders.domain;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryOrderRepository implements OrderRepository {

    private final Map<UUID, OrderEntity> orders = new ConcurrentHashMap<>();

    @Override
    public void save(OrderEntity orderEntity) {
        orders.put(orderEntity.getOrderId(), orderEntity);
    }

    @Override
    public Collection<OrderEntity> findeAlle() {
        return new ArrayList<>(orders.values());
    }

    @Override
    public Optional<OrderEntity> findByOrderIdOptional(UUID orderId) {
        return Optional.ofNullable(orders.get(orderId));
    }
}
