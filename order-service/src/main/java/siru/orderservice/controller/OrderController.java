package siru.orderservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import siru.orderservice.dto.OrderDto;
import siru.orderservice.entity.OrderEntity;
import siru.orderservice.messageQueue.KafkaProducer;
import siru.orderservice.service.OrderService;
import siru.orderservice.vo.RequestOrder;
import siru.orderservice.vo.ResponseOrder;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/order-service")
public class OrderController {

    private final Environment env;
    private final ModelMapper modelMapper;
    private final OrderService orderService;
    private final KafkaProducer kafkaProducer;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It`s Working on Order Service on Port %s", env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable String userId, @RequestBody RequestOrder orderDetails) {
        log.info("Before add order data");
        /* 주문 등록 */
        OrderDto orderDto = modelMapper.map(orderDetails, OrderDto.class);
        orderDto.setUserId(userId);
        OrderDto createdOrder = orderService.createOrder(orderDto);

        ResponseOrder responseOrder = modelMapper.map(createdOrder, ResponseOrder.class);

        /* 주문 이벤트 카프카에 전달 */
        kafkaProducer.send("example_catalog_topic", orderDto);
        log.info("After add order data");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrder(@PathVariable String userId) throws Exception {
        log.info("Before retrieve order data");
        Iterable<OrderEntity> orderList = orderService.getOrdersByUserId(userId);

        List<ResponseOrder> result = new ArrayList<>();
        orderList.forEach(order -> {
            result.add(modelMapper.map(order, ResponseOrder.class));
        });

        try {
            Thread.sleep(1000);
            throw new Exception("장애 발생");
        } catch (InterruptedException ex) {
            log.warn(ex.getMessage());
        }

        log.info("After retrieve order data");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
