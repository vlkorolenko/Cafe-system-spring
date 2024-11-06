package org.example.cafespring.service;

import org.example.cafespring.model.OrderItem;
import org.example.cafespring.repository.OrderItemDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    private final OrderItemDAO orderItemDAO;

    @Autowired
    public OrderItemService(OrderItemDAO orderItemDAO) {
        this.orderItemDAO = orderItemDAO;
    }

    public List<OrderItem> findByOrderId(int orderId) {
        return orderItemDAO.findByOrderId(orderId);
    }

    public void save(OrderItem orderItem) {
        orderItemDAO.add(orderItem);
    }

    public void update(OrderItem orderItem) {
        orderItemDAO.update(orderItem);
    }

    public void delete(int orderItemId) {
        orderItemDAO.delete(orderItemId);
    }
}
