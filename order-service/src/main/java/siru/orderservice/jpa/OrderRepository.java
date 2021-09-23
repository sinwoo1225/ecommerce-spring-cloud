package siru.orderservice.jpa;


import org.springframework.data.repository.CrudRepository;
import siru.orderservice.entity.OrderEntity;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    OrderEntity findByOrderId(String orderId);
    Iterable<OrderEntity> findByUserId(String userId);
}
