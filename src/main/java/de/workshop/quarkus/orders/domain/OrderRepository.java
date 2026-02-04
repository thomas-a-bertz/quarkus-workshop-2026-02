package de.workshop.quarkus.orders.domain;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    void save(OrderEntity orderEntity);
    Collection<OrderEntity> findeAlle();
    Optional<OrderEntity> findByOrderIdOptional(UUID orderId);
}
