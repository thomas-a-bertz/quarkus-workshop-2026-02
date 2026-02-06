package de.workshop.quarkus.orders.domain;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class OrderService {

    private final OrderRepository orderRepository;

    @Inject
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Collection<OrderEntity> getOrders() {
        return orderRepository.findeAlle();
    }

    @Transactional
    public void saveOrder(OrderEntity order) {
        UUID orderId = UUID.randomUUID();
        order.setOrderId(orderId);
        orderRepository.save(order);
    }

    @Transactional
    public Optional<OrderEntity> getOrder(UUID orderId) {
        return orderRepository.findByOrderIdOptional(orderId);
    }
}
