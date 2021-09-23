package siru.orderservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDto {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private Integer stock;
    private Date createdAt;

    private String orderId;
    private String userId;
}
