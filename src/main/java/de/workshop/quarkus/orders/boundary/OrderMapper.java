package de.workshop.quarkus.orders.boundary;

import de.workshop.quarkus.orders.domain.OrderEntity;

public class OrderMapper {

    public static OrderEntity toEntity(OrderDTO orderDTO) {
        OrderEntity entity = new OrderEntity();
        entity.setOrderId(orderDTO.getOrderId());
        entity.setCustomerFirstname(orderDTO.getCustomerFirstname());
        entity.setCustomerLastname(orderDTO.getCustomerLastname());
        entity.setItemDescription(orderDTO.getItemDescription());
        entity.setAmount(orderDTO.getAmount());
        return entity;
    }

    public static OrderDTO toDTO(OrderEntity entity) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(entity.getOrderId());
        dto.setCustomerFirstname(entity.getCustomerFirstname());
        dto.setCustomerLastname(entity.getCustomerLastname());
        dto.setItemDescription(entity.getItemDescription());
        dto.setAmount(entity.getAmount());
        return dto;
    }
}
