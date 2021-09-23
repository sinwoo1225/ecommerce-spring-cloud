package siru.orderservice.service;

import siru.orderservice.dto.OrderDto;
import siru.orderservice.entity.OrderEntity;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDetails);
    OrderDto getOrderByOrderId(String orderId);
    Iterable<OrderEntity> getOrdersByUserId(String userId);
}
