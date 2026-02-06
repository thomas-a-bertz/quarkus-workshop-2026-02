package de.workshop.quarkus.orders.domain;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class PanacheOrderRepository implements OrderRepository, PanacheRepository<OrderEntity> {
    @Override
    public void save(OrderEntity orderEntity) {
        persist(orderEntity);
    }

    @Override
    public Collection<OrderEntity> findeAlle() {
        return listAll();
    }

    public Optional<OrderEntity> findByOrderIdOptional(UUID orderId) {
        return Optional.ofNullable(find("orderId", orderId).firstResult());
    }
}
