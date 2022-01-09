package com.example.stockmanagement.dto;

import com.example.stockmanagement.constant.OrderStatusEnum;
import com.example.stockmanagement.constant.OrderTypeEnum;

public class OrderDto {

    private Integer id;
    private String orderId;
    private OrderTypeEnum type;
    private Integer itemId;
    private Integer orderedQuantity;
    private Integer deliveredQuantity;
    private OrderStatusEnum status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderTypeEnum getType() {
        return type;
    }

    public void setType(OrderTypeEnum type) {
        this.type = type;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(Integer orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }

    public Integer getDeliveredQuantity() {
        return deliveredQuantity;
    }

    public void setDeliveredQuantity(Integer deliveredQuantity) {
        this.deliveredQuantity = deliveredQuantity;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }
}
