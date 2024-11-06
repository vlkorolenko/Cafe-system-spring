package org.example.cafespring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private int orderId;
    private int clientId;
    private int employeeId;
    private LocalDate orderDate;
    private double totalPrice;
    private List<OrderItem> orderItems; // Список позицій замовлення
}
