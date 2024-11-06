package org.example.cafespring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private int orderItemId;
    private int orderId;
    private int dishId;
    private int quantity;
    private double price;
}
